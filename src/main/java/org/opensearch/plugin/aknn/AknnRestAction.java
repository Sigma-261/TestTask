package org.opensearch.plugin.aknn;

import org.opensearch.client.node.NodeClient;
import org.opensearch.common.Table;
import org.opensearch.common.xcontent.XContentBuilder;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.RestStatus;
import org.opensearch.rest.action.cat.AbstractCatAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AknnRestAction extends AbstractCatAction {
    @Override
    // Implement the tasks you want to perform for each of the route
    protected RestChannelConsumer doCatRequest(RestRequest restRequest, NodeClient nodeClient) {
        // Implementation of the task for the route "/_hello/get"
        if(restRequest.path().endsWith("get")){
            return channel -> {
                try {
                    XContentBuilder builder = channel.newBuilder();
                    builder.startObject().field("getRequest", "Hii! Hello World Example Get Request").endObject();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                } catch (final Exception e) {
                    channel.sendResponse(new BytesRestResponse(channel, e));
                }
            };
        }
        // Implementation of the task for the route "/_hello/post"
        else{
            return channel -> {
                String name = restRequest.hasContent()? restRequest.contentParser().mapStrings().toString() : "";
                try {
                    XContentBuilder builder = channel.newBuilder();
                    builder.startObject().field("postRequest", "Hii! Hello World Example Get Request "+ name).endObject();
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
