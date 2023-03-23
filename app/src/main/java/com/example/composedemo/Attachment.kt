package com.example.composedemo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composedemo.ui.theme.ComposeDemoTheme

@Composable
fun AttachmentField() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.Cyan)
    ) {
        Text(
            text = "Attachment",
            color = Color(0xff212121),
            textAlign = TextAlign.Start,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalImagePicker()
    }
}

@Composable
fun ImagePickerButton(onImageSelected: (Uri) -> Unit) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    selectedImageUri = it
                    onImageSelected(it)
                }
            }
        }

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(320.dp)
            .clickable(onClick = {
                Log.e("zxc", "open picker")
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                launcher.launch(intent)
            }),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add image",
                modifier = Modifier.size(32.dp)
            )
            Text(text = stringResource(id = R.string.lc_feedback_upload_image))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HorizontalImagePicker(feedbackViewModel: FeedbackViewModel = viewModel()) {
    val imageListState by feedbackViewModel.imageListState.collectAsState()
    val showImagePicker = imageListState.size < 5
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (showImagePicker) {
            item {
                // Show the image picker in the first card
                ImagePickerButton { uri ->
                    // Do something with the selected image URI
                    Log.d("zxc", "select image, path = ${uri.path}")
                    feedbackViewModel.addImageItem(uri)
                }
            }
        }
        itemsIndexed(imageListState) { index, imageUri ->
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                modifier = Modifier
                    .width(160.dp)
                    .height(280.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                Log.d("zxc", "image clicked, imageUri = ${imageUri.path}")
                            }
                    ) {
                        GlideImage(
                            model = imageUri,
                            contentDescription = imageUri.path,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            feedbackViewModel.removeImageItem(index)
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Remove image",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(imageListState) {}
}

@Preview(showBackground = true)
@Composable
fun AttachmentPreview() {
    ComposeDemoTheme {
        AttachmentField()
    }
}