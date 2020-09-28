package cpf.progressbutton

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Author: cpf
 * Date: 2020/9/28
 * Email: cpf4263@gmail.com
 */
class ProgressButton(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private var mBackgroundDrawable: Drawable

    private var mProgressBackgroundDrawable: Drawable

    private var mProgressDrawable: Drawable

    /**
     * null, 0 ~ 100
     */
    private var mProgressFraction: Float = -1f

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton)
        mBackgroundDrawable =
            arr.getDrawable(R.styleable.ProgressButton_backgroundDrawable)
                ?: ColorDrawable(Color.WHITE)
        mProgressBackgroundDrawable =
            arr.getDrawable(R.styleable.ProgressButton_progressBackgroundDrawable)
                ?: ColorDrawable(Color.LTGRAY)
        mProgressDrawable =
            arr.getDrawable(R.styleable.ProgressButton_progressDrawable)
                ?: ColorDrawable(Color.CYAN)
        val cornerRadius =
            arr.getDimension(R.styleable.ProgressButton_cornerRadius, 0f)
        arr.recycle()
        background = mBackgroundDrawable
        if (cornerRadius > 0) {
            if (mBackgroundDrawable is ColorDrawable) {
                mBackgroundDrawable = GradientDrawable().apply {
                    setColor((mBackgroundDrawable as ColorDrawable).color)
                    setCornerRadius(cornerRadius)
                }
            }
            if (mProgressBackgroundDrawable is ColorDrawable) {
                mProgressBackgroundDrawable = GradientDrawable().apply {
                    setColor((mProgressBackgroundDrawable as ColorDrawable).color)
                    setCornerRadius(cornerRadius)
                }
            }
            if (mProgressDrawable is ColorDrawable) {
                mProgressDrawable = GradientDrawable().apply {
                    setColor((mProgressDrawable as ColorDrawable).color)
                    setCornerRadius(cornerRadius)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (background == null && mProgressFraction >= 0) {
            mProgressBackgroundDrawable.setBounds(0, 0, width, height)
            mProgressBackgroundDrawable.draw(canvas)
            canvas.save()
            val clipRect = mProgressBackgroundDrawable.bounds
            clipRect.right = 0 + (width * mProgressFraction).toInt()
            canvas.clipRect(clipRect)
            mProgressDrawable.setBounds(0, 0, width, height)
            mProgressDrawable.draw(canvas)
            canvas.restore()
        }
        super.onDraw(canvas)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        background = mBackgroundDrawable
        mProgressFraction = -1f
        super.setText(text, type)
    }

    private fun setProgressText(text: CharSequence?) {
        super.setText(text, BufferType.NORMAL)
    }

    fun setProgress(
        progress: Int,
        min: Int = 0,
        max: Int = 100,
        textTemplate: (Int) -> String = { "${it}%" }
    ) {
        if (progress !in min..max) return
        mProgressFraction = (progress - min).toFloat() / (max - min)
        setProgressText(textTemplate(progress))
        background = null
        postInvalidate()
    }
}

