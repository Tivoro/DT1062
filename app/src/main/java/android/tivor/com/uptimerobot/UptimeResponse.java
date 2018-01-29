package android.tivor.com.uptimerobot;

/**
 * http://www.jsonschema2pojo.org/ <--- The real MVP
 * This is a class for the GSON converter to convert the JSON to an object
 **/

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UptimeResponse implements Serializable{
    @SerializedName("stat")
    @Expose
    private String stat;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("monitors")
    @Expose
    private List<Monitor> monitors = null;

    public String getStat() {
        return stat;
    }
    public void setStat(String stat) {
        this.stat = stat;
    }

    public Pagination getPagination() {
        return pagination;
    }
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Monitor> getMonitors() {
        return monitors;
    }
    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    public class Pagination implements Serializable{

        @SerializedName("offset")
        @Expose
        private Integer offset;
        @SerializedName("limit")
        @Expose
        private Integer limit;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getOffset() {
            return offset;
        }
        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Integer getLimit() {
            return limit;
        }
        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getTotal() {
            return total;
        }
        public void setTotal(Integer total) {
            this.total = total;
        }
    }
    public class Monitor implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("friendly_name")
        @Expose
        private String friendlyName;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("sub_type")
        @Expose
        private String subType;
        @SerializedName("keyword_type")
        @Expose
        private String keywordType;
        @SerializedName("keyword_value")
        @Expose
        private String keywordValue;
        @SerializedName("http_username")
        @Expose
        private String httpUsername;
        @SerializedName("http_password")
        @Expose
        private String httpPassword;
        @SerializedName("port")
        @Expose
        private String port;
        @SerializedName("interval")
        @Expose
        private Integer interval;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("create_datetime")
        @Expose
        private Integer createDatetime;

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        public String getFriendlyName() {
            return friendlyName;
        }
        public void setFriendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getType() {
            return type;
        }
        public void setType(Integer type) {
            this.type = type;
        }

        public String getSubType() {
            return subType;
        }
        public void setSubType(String subType) {
            this.subType = subType;
        }

        public String getKeywordType() {
            return keywordType;
        }
        public void setKeywordType(String keywordType) {
            this.keywordType = keywordType;
        }

        public String getKeywordValue() {
            return keywordValue;
        }
        public void setKeywordValue(String keywordValue) {
            this.keywordValue = keywordValue;
        }

        public String getHttpUsername() {
            return httpUsername;
        }
        public void setHttpUsername(String httpUsername) {
            this.httpUsername = httpUsername;
        }

        public String getHttpPassword() {
            return httpPassword;
        }
        public void setHttpPassword(String httpPassword) {
            this.httpPassword = httpPassword;
        }

        public String getPort() {
            return port;
        }
        public void setPort(String port) {
            this.port = port;
        }

        public Integer getInterval() {
            return interval;
        }
        public void setInterval(Integer interval) {
            this.interval = interval;
        }

        public Integer getStatus() {
            return status;
        }
        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCreateDatetime() {
            return createDatetime;
        }
        public void setCreateDatetime(Integer createDatetime) {
            this.createDatetime = createDatetime;
        }
    }
}

