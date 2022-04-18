package com.petamind.example.jetlauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.WindowCompat
import com.petamind.example.jetlauncher.ui.theme.JetLauncherTheme
import java.io.ByteArrayOutputStream
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    color = Color.Transparent
                ) {
                    AppDrawer(name = "Drawer")
                }
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppDrawer(name: String) {
    val pm = LocalContext.current.packageManager
    val resolvedApplist: List<ResolveInfo> = pm
        .queryIntentActivities(
            Intent(Intent.ACTION_MAIN, null)
                .addCategory(Intent.CATEGORY_LAUNCHER), 0
        )

    LazyVerticalGrid(
        cells = GridCells.Adaptive(64.dp)
    ) {
        items(resolvedApplist) {
            val appInfo =
                pm.getApplicationInfo(it.activityInfo.packageName, PackageManager.GET_META_DATA)
            val text = pm.getApplicationLabel(appInfo).toString()
            val appIcon = pm.getApplicationIcon(appInfo)
            Surface(
//                 = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = Color.Yellow,
                        shape = RoundedCornerShape(corner = CornerSize(8.dp))
                    )
                    .padding(8.dp)
            ) {
                Image(bitmap = appIcon.toBitmap().asImageBitmap(), "Icon")
                Text(text = text, style = MaterialTheme.typography.subtitle1, maxLines = 1)
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetLauncherTheme {
        AppDrawer("Android")
    }
}