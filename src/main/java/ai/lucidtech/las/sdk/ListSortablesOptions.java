package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class ListSortablesOptions<T> extends ListResourcesOptions<T> {
    protected String sortBy;
    protected Order order;


    public ListSortablesOptions(){
        this.sortBy = null;
        this.order = null;
    }

    public T setSortBy(String sortBy){
        this.sortBy = sortBy;
        return (T) this;
    }

    public T setOrder(Order order){
        this.order = order;
        return (T) this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        return this.addOptions(parameters);
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters){
        if( this.sortBy != null){
            parameters.add(new BasicNameValuePair("sortBy", this.sortBy));
        }
        if(this.order != null){
            parameters.add(new BasicNameValuePair("order", this.order.value));
        }
        return super.addOptions(parameters);
    }
}
