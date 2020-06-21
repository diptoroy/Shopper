package com.atcampus.shopper.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.atcampus.shopper.Fragment.ProductSpeceficationFragment;
import com.atcampus.shopper.Fragment.ProductsDescriptionFragment;

public class ProductsDescriptionAdapter extends FragmentPagerAdapter {

    private int totalTabs;

    public ProductsDescriptionAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductsDescriptionFragment productsDescriptionFragment1 = new ProductsDescriptionFragment();
                return productsDescriptionFragment1;
            case 1:
                ProductSpeceficationFragment productSpeceficationFragment = new ProductSpeceficationFragment();
                return productSpeceficationFragment;
            case 2:
                ProductsDescriptionFragment productsDescriptionFragment2 = new ProductsDescriptionFragment();
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

