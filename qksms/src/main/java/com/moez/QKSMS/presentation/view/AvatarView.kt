package com.moez.QKSMS.presentation.view

import android.content.Context
import android.content.res.ColorStateList
import android.telephony.PhoneNumberUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.moez.QKSMS.R
import com.moez.QKSMS.common.di.AppComponentManager
import com.moez.QKSMS.common.util.GlideApp
import com.moez.QKSMS.common.util.ThemeManager
import com.moez.QKSMS.data.model.Contact
import kotlinx.android.synthetic.main.avatar_view.view.*
import javax.inject.Inject

class AvatarView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {

    @Inject lateinit var themeManager: ThemeManager

    var contact: Contact? = null
        set(value) {
            field = value
            updateView()
        }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        View.inflate(context, R.layout.avatar_view, this)
        AppComponentManager.appComponent.inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setBackgroundResource(R.drawable.circle)
        clipToOutline = true
        backgroundTintList = ColorStateList.valueOf(themeManager.color)

        if (!isInEditMode) {
            updateView()
        }
    }

    private fun updateView() {
        if (contact?.name.orEmpty().isNotEmpty()) {
            initial.text = contact?.name?.substring(0, 1)
            icon.visibility = GONE
        } else {
            initial.text = null
            icon.visibility = VISIBLE
        }

        photo.setImageDrawable(null)
        contact?.let { contact ->
            GlideApp.with(photo).load(PhoneNumberUtils.stripSeparators(contact.address)).into(photo)
        }
    }
}