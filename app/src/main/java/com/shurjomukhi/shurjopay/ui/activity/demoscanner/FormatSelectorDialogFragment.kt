package com.shurjomukhi.shurjopay.ui.activity.demoscanner

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.shurjomukhi.shurjopay.R
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.*

class FormatSelectorDialogFragment : DialogFragment() {
  interface FormatSelectorDialogListener {
    fun onFormatsSaved(selectedIndices: ArrayList<Int>?)
  }

  private var mSelectedIndices: ArrayList<Int>? = null
  private var mListener: FormatSelectorDialogListener? = null
  override fun onCreate(state: Bundle?) {
    super.onCreate(state)
    retainInstance = true
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    if (mSelectedIndices == null || mListener == null) {
      dismiss()
    }
    val formats =
      arrayOfNulls<String>(ZXingScannerView.ALL_FORMATS.size)
    val checkedIndices = BooleanArray(ZXingScannerView.ALL_FORMATS.size)
    for ((i, format) in ZXingScannerView.ALL_FORMATS.withIndex()) {
      formats[i] = format.toString()
      checkedIndices[i] = mSelectedIndices!!.contains(i)
    }
    val builder = AlertDialog.Builder(activity)
    // Set the dialog title
    builder.setTitle(R.string.choose_formats) // Specify the list array, the items to be selected by default (null for none),
      // and the listener through which to receive callbacks when items are selected
      .setMultiChoiceItems(
        formats, checkedIndices
      ) { dialog, which, isChecked ->
        if (isChecked) {
          // If the user checked the item, add it to the selected items
          mSelectedIndices!!.add(which)
        } else if (mSelectedIndices!!.contains(which)) {
          // Else, if the item is already in the array, remove it
          mSelectedIndices!!.removeAt(mSelectedIndices!!.indexOf(which))
        }
      } // Set the action buttons
      .setPositiveButton(R.string.ok_button) { dialog, id -> // User clicked OK, so save the mSelectedIndices results somewhere
        // or return them to the component that opened the dialog
        if (mListener != null) {
          mListener!!.onFormatsSaved(mSelectedIndices)
        }
      }
      .setNegativeButton(R.string.cancel_button) { dialog, id -> }
    return builder.create()
  }

  companion object {
    fun newInstance(
      listener: FormatSelectorDialogListener?,
      selectedIndices: ArrayList<Int>?
    ): FormatSelectorDialogFragment {
      var selectedIndices = selectedIndices
      val fragment =
        FormatSelectorDialogFragment()
      if (selectedIndices == null) {
        selectedIndices = ArrayList()
      }
      fragment.mSelectedIndices = ArrayList(selectedIndices)
      fragment.mListener = listener
      return fragment
    }
  }
}