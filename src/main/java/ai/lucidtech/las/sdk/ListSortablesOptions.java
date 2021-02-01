package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class ListSortablesOptions<T> extends ListResourcesOptions<T> {
    protected List<String> status;
    protected String sortBy;
    protected String order;


    public ListSortablesOptions(){
        this.status = null;
        this.sortBy = null;
        this.order = null;
    }

    public ListSortablesOptions(String status, String sortBy, String order){
        this.status = Arrays.asList(status);
        this.sortBy = sortBy;
        this.order = order;

    }

    public T setStatus(List<String> status){
        this.status = status;
        return (T) this;
    }

    public T setStatus(String status){
        this.status = Arrays.asList(status);
        return (T) this;
    }

    public T setSortBy(String sortBy){
        this.sortBy = sortBy;
        return (T) this;
    }

    public T setOrder(String order){
        this.order = order;
        return (T) this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        return this.addOptions(parameters);
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters){
        if( this.status != null){
            for (String s : this.status) {
                parameters.add(new BasicNameValuePair("status", s));
            }
        }
        if( this.sortBy != null){
            parameters.add(new BasicNameValuePair("sortBy", this.sortBy));
        }
        if(this.order != null){
            parameters.add(new BasicNameValuePair("order", this.order));
        }
        return super.addOptions(parameters);
    }
}
