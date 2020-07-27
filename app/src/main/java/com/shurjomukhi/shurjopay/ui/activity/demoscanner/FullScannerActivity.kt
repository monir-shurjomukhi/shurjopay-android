package com.shurjomukhi.shurjopay.ui.activity.demoscanner

import android.media.RingtoneManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.DialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.ui.activity.demoscanner.CameraSelectorDialogFragment.CameraSelectorDialogListener
import com.shurjomukhi.shurjopay.ui.activity.demoscanner.FormatSelectorDialogFragment.FormatSelectorDialogListener
import com.shurjomukhi.shurjopay.ui.activity.demoscanner.MessageDialogFragment.Companion.newInstance
import com.shurjomukhi.shurjopay.ui.activity.demoscanner.MessageDialogFragment.MessageDialogListener
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler
import java.util.*

class FullScannerActivity : BaseScannerActivity(), MessageDialogListener,
  ResultHandler, FormatSelectorDialogListener, CameraSelectorDialogListener {
  private var mScannerView: ZXingScannerView? = null
  private var mFlash = false
  private var mAutoFocus = false
  private var mSelectedIndices: ArrayList<Int>? = null
  private var mCameraId = -1
  public override fun onCreate(state: Bundle?) {
    super.onCreate(state)
    if (state != null) {
      mFlash = state.getBoolean(FLASH_STATE, false)
      mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true)
      mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS)
      mCameraId = state.getInt(CAMERA_ID, -1)
    } else {
      mFlash = false
      mAutoFocus = true
      mSelectedIndices = null
      mCameraId = -1
    }
    setContentView(R.layout.activity_simple_scanner)
    setupToolbar()
    val contentFrame = findViewById<View>(R.id.content_frame) as ViewGroup
    mScannerView = ZXingScannerView(this)
    setupFormats()
    contentFrame.addView(mScannerView)
  }

  public override fun onResume() {
    super.onResume()
    mScannerView!!.setResultHandler(this)
    mScannerView!!.startCamera(mCameraId)
    mScannerView!!.flash = mFlash
    mScannerView!!.setAutoFocus(mAutoFocus)
  }

  public override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putBoolean(FLASH_STATE, mFlash)
    outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus)
    outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices)
    outState.putInt(CAMERA_ID, mCameraId)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    var menuItem: MenuItem?
    menuItem = if (mFlash) {
      menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on)
    } else {
      menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off)
    }
    MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER)
    menuItem = if (mAutoFocus) {
      menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on)
    } else {
      menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off)
    }
    MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER)
    menuItem = menu.add(Menu.NONE, R.id.menu_formats, 0, R.string.formats)
    MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER)
    menuItem =
      menu.add(Menu.NONE, R.id.menu_camera_selector, 0, R.string.select_camera)
    MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle presses on the action bar items
    return when (item.itemId) {
      R.id.menu_flash -> {
        mFlash = !mFlash
        if (mFlash) {
          item.setTitle(R.string.flash_on)
        } else {
          item.setTitle(R.string.flash_off)
        }
        mScannerView!!.flash = mFlash
        true
      }
      R.id.menu_auto_focus -> {
        mAutoFocus = !mAutoFocus
        if (mAutoFocus) {
          item.setTitle(R.string.auto_focus_on)
        } else {
          item.setTitle(R.string.auto_focus_off)
        }
        mScannerView!!.setAutoFocus(mAutoFocus)
        true
      }
      R.id.menu_formats -> {
        val fragment: DialogFragment =
          FormatSelectorDialogFragment.newInstance(this, mSelectedIndices)
        fragment.show(supportFragmentManager, "format_selector")
        true
      }
      R.id.menu_camera_selector -> {
        mScannerView!!.stopCamera()
        val cFragment: DialogFragment =
          CameraSelectorDialogFragment.newInstance(this, mCameraId)
        cFragment.show(supportFragmentManager, "camera_selector")
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun handleResult(rawResult: Result) {
    try {
      val notification =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
      val r = RingtoneManager.getRingtone(applicationContext, notification)
      r.play()
    } catch (e: Exception) {
    }
    showMessageDialog(
      "Contents = " + rawResult.text + ", Format = " + rawResult.barcodeFormat.toString()
    )
  }

  fun showMessageDialog(message: String?) {
    val fragment: DialogFragment =
      newInstance("Scan Results", message, this)
    fragment.show(supportFragmentManager, "scan_results")
  }

  fun closeMessageDialog() {
    closeDialog("scan_results")
  }

  fun closeFormatsDialog() {
    closeDialog("format_selector")
  }

  fun closeDialog(dialogName: String?) {
    val fragmentManager = supportFragmentManager
    val fragment =
      fragmentManager.findFragmentByTag(dialogName) as DialogFragment?
    fragment?.dismiss()
  }

  override fun onDialogPositiveClick(dialog: DialogFragment?) {
    // Resume the camera
    mScannerView!!.resumeCameraPreview(this)
  }

  override fun onFormatsSaved(selectedIndices: ArrayList<Int>?) {
    mSelectedIndices = selectedIndices
    setupFormats()
  }

  override fun onCameraSelected(cameraId: Int) {
    mCameraId = cameraId
    mScannerView!!.startCamera(mCameraId)
    mScannerView!!.flash = mFlash
    mScannerView!!.setAutoFocus(mAutoFocus)
  }

  private fun setupFormats() {
    val formats: MutableList<BarcodeFormat> =
      ArrayList()
    if (mSelectedIndices == null || mSelectedIndices!!.isEmpty()) {
      mSelectedIndices = ArrayList()
      for (i in ZXingScannerView.ALL_FORMATS.indices) {
        mSelectedIndices!!.add(i)
      }
    }
    for (index in mSelectedIndices!!) {
      formats.add(ZXingScannerView.ALL_FORMATS[index])
    }
    if (mScannerView != null) {
      mScannerView!!.setFormats(formats)
    }
  }

  public override fun onPause() {
    super.onPause()
    mScannerView!!.stopCamera()
    closeMessageDialog()
    closeFormatsDialog()
  }

  companion object {
    private const val FLASH_STATE = "FLASH_STATE"
    private const val AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE"
    private const val SELECTED_FORMATS = "SELECTED_FORMATS"
    private const val CAMERA_ID = "CAMERA_ID"
  }
}