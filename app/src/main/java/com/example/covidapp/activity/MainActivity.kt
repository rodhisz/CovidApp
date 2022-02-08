package com.example.covidapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covidapp.R
import com.example.covidapp.api.ApiConfig
import com.example.covidapp.api.ApiService
import com.example.covidapp.databinding.ActivityMainBinding
import com.example.covidapp.fragment.BerandaFragment
import com.example.covidapp.fragment.InfoAppFragment
import com.example.covidapp.fragment.ProvinsiFragment
import com.example.covidapp.model.ResponseCovidItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_beranda.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val fragHome : Fragment = BerandaFragment()
    val fragProvinsi : Fragment = ProvinsiFragment()
    val fragInfo : Fragment = InfoAppFragment()

    val fm: FragmentManager = supportFragmentManager
    var active:Fragment = fragHome

    private lateinit var menuItem : MenuItem
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showdataindonesia()

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        setupBottomNav()
    }

    private fun showdataindonesia() {
         ApiConfig.instance.getData().enqueue(object : Callback<ArrayList<ResponseCovidItem>>{
             override fun onResponse(
                 call: Call<ArrayList<ResponseCovidItem>>,
                 response: Response<ArrayList<ResponseCovidItem>>
             ) {
                 if (response.isSuccessful){
                     val indonesiaResponse = response.body()!![0]
                     tv_sembuh.text = indonesiaResponse.sembuh
                     tv_rawat.text = indonesiaResponse.dirawat
                     tv_meninggal.text = indonesiaResponse.meninggal
                     tv_positif.text = indonesiaResponse.positif
                 }
             }

             override fun onFailure(call: Call<ArrayList<ResponseCovidItem>>, t: Throwable) {
                 Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
             }

         })
    }

    private fun setupBottomNav() {

        fm.beginTransaction().add(R.id.nav_container, fragHome).show(fragHome).commit()
        fm.beginTransaction().add(R.id.nav_container, fragProvinsi).hide(fragProvinsi).commit()
        fm.beginTransaction().add(R.id.nav_container, fragInfo).hide(fragInfo).commit()

        menu = btn_nav_view.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        btn_nav_view.setOnNavigationItemSelectedListener { item->

            when(item.itemId){
                R.id.navigation_beranda -> {
                    callFrag(0,fragHome)

                }
                R.id.navigation_provinsi -> {
                    callFrag(1,fragProvinsi)
                }
                R.id.navigation_info_app -> {
                    callFrag(2,fragInfo)
                }
            }

            false }

    }

    private fun callFrag(i: Int, fragment: Fragment) {
        menuItem = menu.getItem(i)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment

    }
}