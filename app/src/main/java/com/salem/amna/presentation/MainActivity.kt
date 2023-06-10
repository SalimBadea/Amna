package com.salem.amna.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import com.salem.amna.R
import com.salem.amna.base.BaseActivity
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.ActivityMainBinding
import com.salem.amna.presentation.ui.categories.CategoriesFragment
import com.salem.amna.presentation.ui.courses.CoursesFragment
import com.salem.amna.presentation.ui.earnings.MyEarningsFragment
import com.salem.amna.presentation.ui.home.HomeFragment
import com.salem.amna.presentation.ui.my_account.MyAccountFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var localePreference: LocalePreference

    var navPosition = 0
    private var token: String? = null

    private lateinit var navController: NavController

    override fun initVar() {
        setContentView(binding.root)

        GlobalScope.launch {
            token = localePreference.getToken()
        }
//        val animation2: Animation =
//            AnimationUtils.loadAnimation(applicationContext, R.anim.top_to_bottom)
//        binding.btnAddCustom.startAnimation(animation2)

        navPosition = intent.getIntExtra("position", 0)
        init()
        when (navPosition) {

            4 -> {
                replaceFragment(MyAccountFragment(), R.id.fragmentContainer, false)
                openFragmentByPosition()
            }

            3 -> {
                replaceFragment(MyEarningsFragment(), R.id.fragmentContainer, false)
                openFragmentByPosition()
            }

            2 -> {
                replaceFragment(CategoriesFragment(), R.id.fragmentContainer, true)
                openFragmentByPosition()
            }

            1 -> {
                replaceFragment(CoursesFragment(), R.id.fragmentContainer, false)
                openFragmentByPosition()
            }

            else -> {
                replaceFragment(HomeFragment(), R.id.fragmentContainer, false)
                openFragmentByPosition()
            }
        }


    }

    override fun onEvent() {
    }

    // init bottom navigation
    @OptIn(DelicateCoroutinesApi::class)
    private fun init() {
        binding.navView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navPosition = 0
                    replaceFragment(HomeFragment(), R.id.fragmentContainer, false)
                }
                R.id.navigation_courses -> {
                    navPosition = 1
                    replaceFragment(CoursesFragment(), R.id.fragmentContainer, true)
                }
                R.id.navigation_add -> {
                    GlobalScope.launch {
                        withContext(Dispatchers.Default) {
                            if (animate())
                                gotoDeliveryItems()
                        }
                    }
                }

                R.id.navigation_earnings -> {
                    navPosition = 3
                    replaceFragment(MyEarningsFragment(), R.id.fragmentContainer, false)
                }
                R.id.navigation_profile -> {
                    navPosition = 4
                    replaceFragment(MyAccountFragment(), R.id.fragmentContainer, false)
                }
            }
            true
        }

        binding.btnAddCustom.setOnClickListener {
//            if (token.isNullOrEmpty()) {
//                showToast(getString(R.string.login_note_message))
//            } else
                GlobalScope.launch {
                    withContext(Dispatchers.Default) {
                        if (animate())
                            gotoDeliveryItems()
                    }
                }
        }
    }

    // fragments transaction
    private fun openFragmentByPosition() {
        binding.navView.selectedItemId = when (navPosition) {
            0 -> R.id.navigation_home
            1 -> R.id.navigation_courses
            2 -> R.id.navigation_add
            3 -> R.id.navigation_earnings
            4 -> R.id.navigation_profile
            else -> R.id.navigation_home
        }
    }

    suspend fun animate(): Boolean {
        delay(100)
        navPosition = 2
        val animation2: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_to_top)
        binding.btnAddCustom.startAnimation(animation2)
        binding.btnAdd.startAnimation(animation2)
        return true
    }

    suspend fun gotoDeliveryItems() {
        delay(1800)
        replaceFragment(CategoriesFragment(), R.id.fragmentContainer, true)
    }

    override fun observeData() {

    }
}