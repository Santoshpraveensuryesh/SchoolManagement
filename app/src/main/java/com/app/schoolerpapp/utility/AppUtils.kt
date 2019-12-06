package com.viewmodel.app.utility


import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.SystemClock
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.app.schoolerpapp.BuildConfig
import com.app.schoolerpapp.R
import com.google.android.material.snackbar.Snackbar

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class AppUtils {


    // variable to track event time
    private var mLastClickTime: Long = 0

    fun perfomroneClick() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
    }

    companion object {
        val CAMERA_REQUEST_CODE = 1001
        val GALLERY_REQUEST_CODE = 1002
        lateinit var cameraFilePath: String
        private val progress: ProgressBar? = null

        fun loadFragment(
            loadFragment: Fragment?,
            activity: FragmentActivity,
            id: Int,
            tag: String
        ) {
            if (loadFragment != null) {
                activity.supportFragmentManager
                if (!loadFragment.isAdded) {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(id, loadFragment, tag)
                        .setCustomAnimations(
                            R.anim.slide_in_left,
                            R.anim.slide_out_right,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                        )
                        .commitAllowingStateLoss()
                }
            }
        }

        fun replaceFragment(fragment: Fragment, activity: FragmentActivity, id: Int) {
            val backStateName = fragment.javaClass.name
            val manager = activity.supportFragmentManager
            val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)

            if (!fragmentPopped) { //fragment not in back stack, create it.
                val ft = manager.beginTransaction()
                ft.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                //            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(id, fragment, backStateName)
                ft.addToBackStack(null)
                ft.commit()
            }
        }

        fun clearbackstatck(activity: FragmentActivity) {
            val fm = activity.supportFragmentManager
            for (i in 0 until fm.backStackEntryCount) {
                fm.popBackStack()
            }
        }

        fun hideSoftKeyboard(activity: Activity) {
            try {
                if (activity.currentFocus == null) {
                    return
                }
                val inputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun showSoftKeyboard(activity: Activity) {
            try {
                if (activity.currentFocus == null) {
                    return
                }
                val inputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 1)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun hideSoftKeyboardDialog(editText: EditText, activity: Activity) {
            try {
                editText.postDelayed({
                    val imm =
                        editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(
                        editText.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }, 100)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        internal var progressBar: ContentLoadingProgressBar? = null

        //    public static void startProgress2(Activity activity) {
        //        progressBar = activity.findViewById(R.id.progressBar);
        //        progressBar.bringToFront();
        //        progressBar.show();
        //    }
        //
        //    public static void stopProgress2(Activity activity) {
        //        if (progressBar != null)
        //            progressBar.hide();
        //    }

        //    public static void startProgress(Activity activity) {
        //        progress = new ProgressDialog(activity);
        //        progress.setCanceledOnTouchOutside(false);
        //        progress.setMessage("Please Wait...");
        //        progress.setCancelable(false);
        //        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
        //            Drawable drawable = new ProgressBar(activity).getIndeterminateDrawable().mutate();
        //            drawable.setColorFilter(ContextCompat.getColor(activity, R.color.colorAccent),
        //                    PorterDuff.Mode.SRC_IN);
        //            progress.setIndeterminateDrawable(drawable);
        //        }
        //        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //        progress.show();
        //    }

        fun stopProgress(activity: Activity) {
            if (progress!!.isActivated) {
                progress.visibility = View.GONE
            }
        }


        fun compressImage(imageUri: Uri, activity: Context): Bitmap? {

            val filePath = RealPathUtil.getRealPath(activity, imageUri)
            var scaledBitmap: Bitmap? = null

            val options = BitmapFactory.Options()

            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)

            var actualHeight = options.outHeight
            var actualWidth = options.outWidth

            //      max Height and width values of the compressed image is taken as 816x612

            val maxHeight = 816.0f
            val maxWidth = 612.0f
            var imgRatio = (actualWidth / actualHeight).toFloat()
            val maxRatio = maxWidth / maxHeight

            //      width and height values are set maintaining the aspect ratio of the image

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()

                }
            }

            //      setting inSampleSize value allows to load a scaled down version of the original image

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

            //      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false

            //      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)

            try {
                //          load the bitmap from its path
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()

            }

            try {
                scaledBitmap =
                    Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }

            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f

            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

            val canvas = Canvas(scaledBitmap!!)
            canvas.setMatrix(scaleMatrix)
            canvas.drawBitmap(
                bmp,
                middleX - bmp.width / 2,
                middleY - bmp.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )

            //      check the rotation of the image and display it properly
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath)

                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0
                )
                Log.d("EXIF", "Exif: $orientation")
                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90f)
                    Log.d("EXIF", "Exif: $orientation")
                } else if (orientation == 3) {
                    matrix.postRotate(180f)
                    Log.d("EXIF", "Exif: $orientation")
                } else if (orientation == 8) {
                    matrix.postRotate(270f)
                    Log.d("EXIF", "Exif: $orientation")
                }
                scaledBitmap = Bitmap.createBitmap(
                    scaledBitmap, 0, 0,
                    scaledBitmap.width, scaledBitmap.height, matrix,
                    true
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }

            var out: FileOutputStream? = null
            val filename = filename
            try {
                out = FileOutputStream(filename)

                scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            return scaledBitmap

        }

        val filename: String
            get() {
                val file = File(Environment.getExternalStorageDirectory().path, "MyFolder/Images")
                if (!file.exists()) {
                    file.mkdirs()
                }
                return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"

            }

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }

            return inSampleSize
        }

        fun showBar(view: View, msg: String, context: Context) {
            val snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.RED)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(context.resources.getColor(R.color.colorAccent))
            val textView =
                snackbarView.findViewById<View>(R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            textView.textSize = 18f
            snackbar.show()
        }

        fun confirmationDialog(
            activity: Activity,
            title: String,
            confirmationListener: ConfirmationListener
        ) {
            var dialog = Dialog(activity, R.style.dialog_slide_animation)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            // Include dialog.xml file
            dialog.setContentView(R.layout.confirmation_pop_up_dialog)



            val width = activity.resources.displayMetrics.widthPixels
            dialog.window!!.setLayout(width - 100, ConstraintLayout.LayoutParams.WRAP_CONTENT)

            var txtConfirmTitle = dialog.findViewById<TextView>(R.id.txt_confirm_title)
            var btnYes = dialog.findViewById<Button>(R.id.btn_yes)
            var btnNo = dialog.findViewById<Button>(R.id.btn_no)
            txtConfirmTitle.setText(title)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AppUtils2.applyRipple(btnYes, activity)
                AppUtils2.applyRipple(btnNo, activity)
            } else {
                AppUtils.setEffectbelow21(activity,btnYes)
                AppUtils.setEffectbelow21(activity,btnNo)
            }

            btnYes.setOnClickListener(View.OnClickListener {
                confirmationListener.yesClick()
                dialog.dismiss()
            })
            btnNo.setOnClickListener(View.OnClickListener {
                //                confirmationListener.noClck()
                dialog.dismiss()
            })
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setDimAmount(0.2f)
            dialog.show()

        }

        interface ConfirmationListener {
            fun yesClick()
//            fun noClck()
        }

        //    public static void showAlertDialog(final Activity activity, String title, String msg) {
        //        // Create custom dialog object
        ////        Blurry.delete(constaint_main_view);
        ////        final View dialogView = View.inflate(this,R.layout.sharelocation_option_dialog,null);
        //
        //        final Dialog dialog = new Dialog(activity, R.style.dialog_slide_animation);
        //        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //        // Include dialog.xml file
        //        dialog.setContentView(R.layout.send_message_dialog);
        //        TextView txt_error = dialog.findViewById(R.id.txt_error);
        //        txt_error.setText(title);
        //        TextView txt_error_msg = dialog.findViewById(R.id.txt_error_msg);
        //        txt_error_msg.setText(msg);
        //        TextView txt_cross = dialog.findViewById(R.id.txt_cross);
        //        txt_cross.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                dialog.dismiss();
        //            }
        //        });
        //        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        ////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        ////            AppUtils.applyRipple(btn_ok, activity);
        ////        }
        //        btn_ok.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                dialog.dismiss();
        //            }
        //        });
        //        int width = activity.getResources().getDisplayMetrics().widthPixels;
        //        dialog.getWindow().setLayout(width - 100, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        //
        //        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
        //            @Override
        //            public void onShow(DialogInterface dialogInterface) {
        ////                revealShow(dialogView, true, null);
        //            }
        //        });
        //
        //        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
        //
        //            @Override
        //            public boolean onKey(DialogInterface arg0, int keyCode,
        //                                 KeyEvent event) {
        //                if (keyCode == KeyEvent.KEYCODE_BACK) {
        //                    dialog.dismiss();
        //                }
        //                return true;
        //            }
        //        });
        //        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //        dialog.getWindow().setDimAmount(0.5f);
        //        dialog.show();
        //
        //    }





        fun notBlank(str: String?): Boolean {

            return if (str != null && !TextUtils.isEmpty(str)) {
                false
            } else
                true
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        fun isValidEmail(target: CharSequence?): Boolean {
            return if (target == null) {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }

        fun isValidMobile(phone: String?): Boolean {
            var check = false
            if (!Pattern.matches("[a-zA-Z]+", phone)) {
                if (phone!!.length < 6 || phone.length > 13) {
                    // if(phone.length() != 10) {
                    check = false
                } else {
                    check = true
                }
            } else {
                check = false
            }
            return check
        }

        fun getDayofWeek(str: String?): String? {
            val dateformat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date
            try {
                date = dateformat.parse(str)
                val dayFormate = SimpleDateFormat("EE")
                val dayFromDate = dayFormate.format(date)
                return dayFromDate

            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                return ""
            }
        }
        fun getDateFormateMonth(str: String?): String? {
            val dateformat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date
            try {
                date = dateformat.parse(str)
                val dayFormate = SimpleDateFormat("dd MMM, yyyy")
                val dayFromDate = dayFormate.format(date)
                return dayFromDate

            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                return ""
            }
        }

        //pic image

        fun selectImage(activity: Activity) {
            val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Add Photo!")
            builder.setItems(items) { dialog, item ->
                if (items[item] == "Take Photo") {
                    captureFromCamera(activity)
                } else if (items[item] == "Choose from Library") {
                    pickFromGallery(activity)
                } else if (items[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        }

        private fun pickFromGallery(activity: Activity) {
            //Create an Intent with action as ACTION_PICK
            val intent = Intent(Intent.ACTION_PICK)
            // Sets the type as image/*. This ensures only components of type image are selected
            intent.type = "image/*"
            //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            // Launching the Intent
            activity.startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }

        private fun captureFromCamera(activity: Activity) {
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(
                        activity,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile()!!
                    )
                )
                activity.startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }

        @Throws(IOException::class)
        private fun createImageFile(): File? {
            // Create an image file name

            val timeStamp = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
            val imageFileName = "JPEG-$timeStamp-"
            //This is the directory in which the file will be created. This is the default location of Camera photos
            val storageDir = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                ), "Camera"
            )
            // Create the storage directory if it does not exist
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    return null
                }
            }
            val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
            )
            // Save a file: path for using again
            cameraFilePath = "file://" + image.absolutePath
            return image
        }

        fun setEffectbelow21(activity: Activity, view: View) {
//            view.background = activity.resources.getDrawable(R.drawable.ripple_rectangle)
        }

        /* Returns true if url is valid */
        fun isValid(url:String) : Boolean{
            /* Try creating a valid URL */
            try {
                URL (url).toURI();
                return true;
            }

            // If there was an Exception
            // while creating URL object
            catch (e : java.lang.Exception) {
                return false;
            }
        }
        fun isAppBack(activity: FragmentActivity): Boolean {
            val manager = activity.supportFragmentManager
            Log.e("size", manager.backStackEntryCount.toString() + "")
            return if (manager.backStackEntryCount >= 1) { //fragment not in back stack, create it.
                false
            } else true
        }
        @Throws(IOException::class)
        internal fun getAddress(context: Context, lat: Double, lng: Double): Array<String?> {
            val loc = arrayOfNulls<String>(2)
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses = gcd.getFromLocation(lat, lng, 1)
            if (addresses.size > 0) {
                println(addresses[0].locality)
                loc[0] = addresses[0].getAddressLine(0)
                loc[1] = addresses[0].countryCode
                return loc
            } else {
                // do your stuff
                return loc
            }
        }

        internal var progressDialog: ProgressDialog? = null
        fun showProgressDialog(context: Activity): ProgressDialog {

            progressDialog = ProgressDialog(context)
            progressDialog!!.isIndeterminate = false
            progressDialog!!.setCancelable(false)
            progressDialog!!.setMessage("Please wait ...")
            progressDialog!!.show()
            return progressDialog as ProgressDialog
        }

        fun cancelLoading() {
            if (progressDialog != null && progressDialog!!.isShowing)
                progressDialog!!.cancel()

        }
    }



    //    public static void setEffectbelow21(Activity activity, View view) {
    //        view.setBackground(activity.getResources().getDrawable(R.drawable.ripple_rectangle));
    //    }
    //
    //    public static void setEffectbelow21fb(Activity activity, View view) {
    //        view.setBackground(activity.getResources().getDrawable(R.drawable.ripple_fbook));
    //    }
    //
    //    public static void setEffectbelow21gmail(Activity activity, View view) {
    //        view.setBackground(activity.getResources().getDrawable(R.drawable.ripple_gmail));
    //    }
    //    public static void setEffectbelow21white(Activity activity, View view) {
    //        view.setBackground(activity.getResources().getDrawable(R.drawable.ripple_gray));
    //    }
}
