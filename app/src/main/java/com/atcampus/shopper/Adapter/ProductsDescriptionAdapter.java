package com.atcampus.shopper.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.atcampus.shopper.Fragment.ProductSpeceficationFragment;
import com.atcampus.shopper.Fragment.ProductsDescriptionFragment;
import com.atcampus.shopper.Model.ProductsSpeceficationModel;

import java.util.List;

public class ProductsDescriptionAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String pDesc,pOtherDesc;
    private List<ProductsSpeceficationModel> productsSpeceficationModelList;

    public ProductsDescriptionAdapter(@NonNull FragmentManager fm, int totalTabs, String pDesc,String pOtherDesc,List<ProductsSpeceficationModel> productsSpeceficationModelList) {
        super(fm);
        this.totalTabs = totalTabs;
        this.pDesc = pDesc;
        this.pOtherDesc = pOtherDesc;
        this.productsSpeceficationModelList = productsSpeceficationModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductsDescriptionFragment productsDescriptionFragment1 = new ProductsDescriptionFragment();
                productsDescriptionFragment1.body = pDesc;
                return productsDescriptionFragment1;
            case 1:
                ProductSpeceficationFragment productSpeceficationFragment = new ProductSpeceficationFragment();
                productSpeceficationFragment.productsSpeceficationModelList = productsSpeceficationModelList;
                return productSpeceficationFragment;
            case 2:
                ProductsDescriptionFragment productsDescriptionFragment2 = new ProductsDescriptionFragment();
                productsDescriptionFragment2.body = pOtherDesc;
                return productsDescriptionFragment2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

