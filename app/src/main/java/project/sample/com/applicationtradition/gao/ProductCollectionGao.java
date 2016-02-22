
package project.sample.com.applicationtradition.gao;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class ProductCollectionGao {

    @SerializedName("products")
    @Expose
    private List<ProductGao> products = new ArrayList<ProductGao>();
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<ProductGao> getProducts() {
        return products;
    }

    public void setProducts(List<ProductGao> products) {
        this.products = products;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
