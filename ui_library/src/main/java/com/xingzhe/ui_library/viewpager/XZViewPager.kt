package com.xingzhe.ui_library.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.SparseBooleanArray

/**
 * Created by wumm on 2019/5/10.
 */
class XZViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {
    var currentItem: Page? = null
    var mInitStatus: SparseBooleanArray = SparseBooleanArray()
    var setupRunnable: Runnable? = null
    private val setupDelay: Long = 300
    private val mOnPageChangeListener: OnPageChangeListener = MyPageChangeListener()

    init {
        addOnPageChangeListener(mOnPageChangeListener)
    }

    fun showPage(position: Int) {
        if (getCurrentItem() != position) {
            setCurrentItem(position)
        } else {
            post {
                if (adapter is BasePagerAdapter) {
                    val cachedViewAt = (adapter as BasePagerAdapter).getCachedViewAt(context, position)

                    var currentPage: Page? = null
                    if (cachedViewAt is Page) {
                        currentPage = cachedViewAt
                    }

                    if (currentItem != currentPage) {
                        currentItem?.onPageHide()
                    }

                    if (currentPage != null) {
                        if (mInitStatus.get(position)) {
                            currentPage.onPageShow()
                            currentItem = currentPage
                        } else {
                            currentPage.onPageInit()
                            currentPage.onPageShow()
                            currentItem = currentPage
                            mInitStatus.put(position, true)
                        }
                    }

                }
            }

        }
    }


    inner class MyPageChangeListener : OnPageChangeListener {
        override fun onPageScrollStateChanged(p0: Int) {
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        }

        override fun onPageSelected(position: Int) {
            if (adapter is BasePagerAdapter) {
                val cachedViewAt = (adapter as BasePagerAdapter).getCachedViewAt(context, position)

                var currentPage: Page? = null
                if (cachedViewAt is Page) {
                    currentPage = cachedViewAt
                }

                if (currentItem != currentPage) {
                    currentItem?.onPageHide()
                }

                if (currentPage != null) {
                    if (mInitStatus.get(position)) {
                        currentPage.onPageShow()
                        currentItem = currentPage
                    } else {
                        if (setupRunnable != null) {
                            removeCallbacks(setupRunnable)
                        }
                        setupRunnable = Runnable {
                            currentPage.onPageInit()
                            currentPage.onPageShow()
                            currentItem = currentPage
                            mInitStatus.put(position, true)
                            setupRunnable = null
                        }
                        postDelayed(setupRunnable, setupDelay)
                    }
                }

            }
        }

    }
}