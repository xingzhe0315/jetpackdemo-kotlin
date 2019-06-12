package com.xingzhe.ui_library.ptr

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.xingzhe.ui_library.R
import com.xingzhe.ui_library.dataview.BaseObserver
import com.xingzhe.ui_library.dataview.DataRetryHandler

/**
 * Created by wumm on 2019/5/13.
 */
class DefaultLoadMoreView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr), LoadMoreView {
    override var retryHandler: DataRetryHandler? = null
    private var progress: ProgressBar
    private var messageTv: TextView

    init {
        View.inflate(context, R.layout.view_loading_more_footer, this)
        progress = findViewById(R.id.progress)
        messageTv = findViewById(R.id.message_tv)
    }

    override fun onLoadMoreError(error: BaseObserver.ResponseError) {
        progress.visibility = GONE
        messageTv.text = "加载失败，点击重试"
        setOnClickListener {
            onLoading()
            retryHandler?.onDataRetry()
        }
    }

    override fun onLoading() {
        setOnClickListener(null)
        progress.visibility = View.VISIBLE
        messageTv.text = "加载中..."
    }

    override fun onEnd() {
        setOnClickListener(null)
        progress.visibility = GONE
        messageTv.text = "没有更多了"
    }
}