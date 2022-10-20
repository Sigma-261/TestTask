package org.example;

import com.google.gson.annotations.SerializedName;

public class JsonInfo {
    public int ups_adv_output_load;
    public int ups_adv_battery_temperature;
    @SerializedName("@timestamp")
    public String timestamp;
    public String host;
    public int ups_adv_battery_run_time_remaining;
    public int ups_adv_output_voltage;

    public JsonInfo() {
    }
}
