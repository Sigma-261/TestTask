package org.opensearch.plugin.aknn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.JsonInfo;
import org.opensearch.action.admin.indices.create.CreateIndexRequest;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.node.NodeClient;
import org.opensearch.common.Table;
import org.opensearch.common.xcontent.XContentBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.RestStatus;
import org.opensearch.rest.action.cat.AbstractCatAction;
import org.opensearch.search.SearchHit;
import org.opensearch.search.SearchHits;
import org.opensearch.search.builder.SearchSourceBuilder;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class AknnRestAction extends AbstractCatAction {
    @Override
    // Implement the tasks you want to perform for each of the route
    protected RestChannelConsumer doCatRequest(RestRequest restRequest, NodeClient nodeClient) {
        // Implementation of the task for the route "/_hello/get"
        if(restRequest.path().endsWith("get")){
            return channel -> {
                try {
                    SearchRequest searchRequest = new SearchRequest("test");
                    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
                    searchSourceBuilder.size(10000);
                    searchRequest.source(searchSourceBuilder);

                    SearchResponse searchClient = nodeClient.search(searchRequest).get();

                    SearchHits hits = searchClient.getHits();

                    SearchHit[] searchHits = hits.getHits();

                    List<Map<String,Object>> sourceAsMap = new ArrayList<>();

                    for (SearchHit hit : searchHits) {;
                        sourceAsMap.add(hit.getSourceAsMap());
                    }

                    //get mav value
                    int max = sourceAsMap.stream()
                            .filter(v -> (int) v.get("ups_adv_output_voltage") != 0)
                            .mapToInt(v -> (int) v.get("ups_adv_output_voltage"))
                            .max().orElseThrow(NoSuchElementException::new);

                    //get avg value
                    double avg = sourceAsMap.stream()
                            .filter(v -> (int) v.get("ups_adv_battery_run_time_remaining") != 0)
                            .mapToInt(v -> (int) v.get("ups_adv_battery_run_time_remaining"))
                            .average()
                            .orElse(0.0);

                    //get unique values
                    List<String> values = sourceAsMap.stream()
                            .filter(v -> v.get("host") != null)
                            .map(v -> (String) v.get("host"))
                            .distinct()
                            .collect(Collectors.toList());

                    XContentBuilder builder = channel.newBuilder();
                    builder.startObject().field("getRequest", "max[ups_adv_output_voltage]:" + max
                            +", avg[ups_adv_battery_run_time_remaining]:" + avg
                            + ", values[host]" + values).endObject();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                } catch (final Exception e) {
                    channel.sendResponse(new BytesRestResponse(channel, e));
                }
            };
        }
        // Implementation of the task for the route "/_hello/post"
        else{
            return channel -> {

                try {
                    XContentBuilder builder = channel.newBuilder();
                    builder.startObject().field("postRequest", "post").endObject();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                } catch (final Exception e) {
                    channel.sendResponse(new BytesRestResponse(channel, e));
                }
            };
        }
    }

    @Override
    protected void documentation(StringBuilder stringBuilder) {
        stringBuilder.append(documentation());
    }

    public static String documentation() {
        return "/_hello/example\n";
    }

    @Override
    protected Table getTableWithHeader(RestRequest restRequest) {
        final Table table = new Table();
        table.startHeaders();
        table.addCell("test", "desc:test");
        table.endHeaders();
        return table;
    }

    @Override
    public String getName() {
        return "rest_handler_hello_world";
    }

    @Override
    // Declare all the routes here
    public List<Route> routes() {
        return new ArrayList<>(Arrays.asList(
                new Route(RestRequest.Method.GET, "/_hello/get"),
                new Route(RestRequest.Method.POST, "/_hello/post")
        ));
    }
}
