package com.example.composedemo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun SelectICSFileButton() {
    val context = LocalContext.current
    val selectFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Log.w("zxc", "uri = $uri")
            uri?.let {
                sendIntentToHandleICSFile(context, it)
            }
        }

    Button(onClick = { selectFileLauncher.launch("text/calendar") }) {
        Text("選擇 ICS 文件")
    }
}

private fun sendIntentToHandleICSFile(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(uri, "text/calendar")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(Intent.createChooser(intent, ""))
}


fun foo(context: Context) {
    val intent = Intent(Intent.ACTION_INSERT)
    intent.data = CalendarContract.Events.CONTENT_URI
    intent.putExtra(CalendarContract.Events.TITLE, "新增活動")
    intent.putExtra(CalendarContract.Events.DESCRIPTION, "這是一個新增的活動")
    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "台北市")
    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Date().time + 10000)
    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Date().time + 100000)
    intent.putExtra(CalendarContract.Events.ALL_DAY, false)
    intent.putExtra(CalendarContract.Events.HAS_ALARM, false)
    context.startActivity(Intent.createChooser(intent, ""))
}

fun sendEmailWithAttachment(
    context: Context,
    emailAddresses: Array<String?>?,
    subject: String?,
    body: String?,
    attachmentUri: Uri?
) {
    val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
    emailSelectorIntent.data = Uri.parse("mailto:")
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_EMAIL, emailAddresses)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, body)
    intent.putExtra(Intent.EXTRA_STREAM, attachmentUri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.selector = emailSelectorIntent
    context.startActivity(Intent.createChooser(intent, "Send Email"))
}

fun handleICS(context: Context) {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, 2023)
    calendar.set(Calendar.MONTH, Calendar.FEBRUARY)
    calendar.set(Calendar.DAY_OF_MONTH, 28)
    calendar.set(Calendar.HOUR_OF_DAY, 14)
    calendar.set(Calendar.MINUTE, 30)

    val startTime = calendar.timeInMillis
    val endTime = startTime + 60 * 60 * 1000 // 1 hour

    val eventTitle = "測試事件"
    val eventDescription = "這是一個測試事件"
    val eventLocation = "測試地點"

    val icsFile = File(context.getExternalFilesDir(null), "$eventTitle.ics")
    icsFile.outputStream().bufferedWriter().use { writer ->
        writer.write("BEGIN:VCALENDAR\n")
        writer.write("VERSION:2.0\n")
        writer.write("PRODID:-//My Application//My Event//EN\n")
        writer.write("BEGIN:VEVENT\n")
        writer.write("UID:${UUID.randomUUID()}\n")
        writer.write("DTSTART:${getTimeString(startTime)}\n")
        writer.write("DTEND:${getTimeString(endTime)}\n")
        writer.write("SUMMARY:$eventTitle\n")
        writer.write("DESCRIPTION:$eventDescription\n")
        writer.write("LOCATION:$eventLocation\n")
        writer.write("END:VEVENT\n")
        writer.write("END:VCALENDAR\n")
    }
    val file = File(context.getExternalFilesDir(null), "測試事件.ics")
    val fileUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    sendEmailWithAttachment(
        context = context,
        emailAddresses = arrayOf("bryantcheng@m800.com"),
        subject = "title",
        body = "body",
        attachmentUri = fileUri
    )
}

private fun getTimeString(timeInMillis: Long): String {
    val sdf = SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(timeInMillis)
}