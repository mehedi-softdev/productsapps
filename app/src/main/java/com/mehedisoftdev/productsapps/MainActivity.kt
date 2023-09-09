package com.mehedisoftdev.productsapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mehedisoftdev.productsapps.adapters.ProductListAdapter
import com.mehedisoftdev.productsapps.databinding.ActivityMainBinding
import com.mehedisoftdev.productsapps.viewmodels.MainViewModel
import com.mehedisoftdev.productsapps.viewmodels.GeneralViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var mainViewModelFactory: GeneralViewModelFactory

    // adapter
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // setup list adapter
        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.productRecyclerView.setHasFixedSize(true)
        productListAdapter = ProductListAdapter()
        binding.productRecyclerView.adapter = productListAdapter

        (application as ProductApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        mainViewModel.products.observe(this, Observer {
            productListAdapter.submitList(it)
        })

    }
}