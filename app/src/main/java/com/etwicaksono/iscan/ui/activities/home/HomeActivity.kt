package com.etwicaksono.iscan.ui.activities.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.etwicaksono.iscan.R
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.databinding.ActivityHomeBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.ui.activities.scannerToko.ScannerTokoActivity
import com.etwicaksono.iscan.utils.UserPref

class HomeActivity : BaseActivity() , View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.btnScanToko.setOnClickListener(this)
        viewModel = ViewModelProvider(
            this ,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]
        viewModel.getState().observer(this , Observer {
            handlerUIState(it)
        })
    }

    private fun handlerUIState(it: HomeState?) {
        when (it) {
            is HomeState.IsLoading -> showLoading(binding.root,it.state)
            is HomeState.Error -> showToast(it.err)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_scan_toko -> {
                startActivity(Intent(this@HomeActivity , ScannerTokoActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showDataToko()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = HomeAdapter { toko ->
            showSelectedData(toko)
        }.apply { notifyDataSetChanged() }

        binding.rvDaftarToko.layoutManager = LinearLayoutManager(this)
        binding.rvDaftarToko.adapter = adapter
    }

    private fun showSelectedData(toko: TokoEntity) {
        startActivity(Intent(this , ScannerTokoActivity::class.java).apply {
            putExtra(ScannerTokoActivity.EXTRAS_DATA , toko)
        })
    }

    private fun showDataToko() {
        UserPref(this).getValues("recent_store")?.let {
            Log.d("RECENT_STORE" , it)
            viewModel.getRecentToko(it)
        }

        viewModel.getToko().observe(this , Observer {
            if (it.isNullOrEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvNoData.text = getString(R.string.no_recent_store)
            } else {
                binding.tvNoData.visibility = View.GONE
                adapter.setToko(it)
            }
        })
    }

}