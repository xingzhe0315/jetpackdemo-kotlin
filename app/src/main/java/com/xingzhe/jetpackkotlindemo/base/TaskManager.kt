package com.xingzhe.jetpackkotlindemo.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.xingzhe.jetpackkotlindemo.headerlist.HeaderListActivity

/**
 * Created by wumm on 2019/5/20.
 */
class TaskManager {
    companion object{
        const val REQUEST_CODE = 100
        fun executeAfterChooseCategory(activity: BaseActivity,action:TaskAction){
            val intent = Intent(activity,HeaderListActivity::class.java)
            activity.startActivityForResult(intent,REQUEST_CODE,object :BaseActivity.OnActivityResultListener{
                override fun onActivityResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?) {
                    if (resultCode == Activity.RESULT_OK){
                        action.action(data)
                    }
                }
            })
        }
    }

    interface TaskAction{
        fun action(data:Intent?)
    }
}