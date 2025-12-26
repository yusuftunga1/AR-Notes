package com.arnot.app.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.ViewAttachmentManager
import io.github.sceneview.ar.ARScene
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.math.Position
import io.github.sceneview.node.Node
import io.github.sceneview.node.ViewNode
import io.github.sceneview.ar.node.HitResultNode

@Composable
fun ScreenTwo(
    onBack: () -> Unit,
    onNavigateToGallery: () -> Unit,
    cameraContent: @Composable () -> Unit = {}
) {
    var isSearchOpen by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var hasCameraPermission by remember { mutableStateOf(false) }
    var addNoteAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var isPlaneFound by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        if (!granted) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
        hasCameraPermission = granted
    }

    val searchOffset by animateDpAsState(
        targetValue = if (isSearchOpen) 0.dp else 720.dp,
        animationSpec = tween(300), label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (hasCameraPermission) {
                ARNoteScene(
                    onAddNoteReady = { addNoteAction = it },
                    onPlaneTracking = { tracking -> isPlaneFound = tracking }
                )
            } else {
                cameraContent()
            }
        }

        // Hafif gradyan overlay (kamera üstü okunabilirlik)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(0.25f),
                            Color.Transparent,
                            Color.Black.copy(0.45f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("9:40 PM", color = Color.White, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White.copy(alpha = 0.18f), CircleShape)
                ) {
                    Icon(Icons.Default.ChevronLeft, null, tint = Color.White)
                }

                IconButton(
                    onClick = { isSearchOpen = !isSearchOpen },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White.copy(alpha = 0.18f), CircleShape)
                ) {
                    Icon(Icons.Default.Search, null, tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Default.Bookmark, null, tint = Color(0xFF0F172A))
                }

                val isNoteReady = addNoteAction != null && isPlaneFound
                Button(
                    onClick = { addNoteAction?.invoke() },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    enabled = isNoteReady,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFACC15),
                        disabledContainerColor = Color(0xFFFACC15).copy(alpha = 0.4f)
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        "Not Ekle",
                        color = Color(0xFF0F172A),
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(
                    onClick = onNavigateToGallery,
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Default.GridView, null, tint = Color(0xFF0F172A))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.BottomCenter)
                .offset(y = searchOffset)
                .background(
                    Color.White.copy(0.95f),
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Notlarını ara",
                        fontSize = 20.sp,
                        color = Color(0xFF0F172A),
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { isSearchOpen = false }) {
                        Icon(Icons.Default.Close, null, tint = Color(0xFF0F172A))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Not ara...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFACC15),
                        cursorColor = Color(0xFFFACC15)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Son aramalar",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                val recentSearches = listOf(
                    "Toplantı notları",
                    "Proje fikirleri",
                    "Kitap notları"
                )

                recentSearches.forEach { search ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE2E8F0).copy(0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.History,
                                contentDescription = null,
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(search, color = Color(0xFF0F172A))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ARNoteScene(
    onAddNoteReady: ((() -> Unit)?) -> Unit = {},
    onPlaneTracking: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val nodes = rememberNodes()
    val viewAttachmentManager = remember { ViewAttachmentManager(context, View(context)) }

    val gestureListener = rememberOnGestureListener(onSingleTapConfirmed = { _: MotionEvent?, hitNode: io.github.sceneview.node.Node? ->
        val hitResult = (hitNode as? HitResultNode)?.hitResult ?: return@rememberOnGestureListener
        if (hitResult.trackable is Plane && (hitResult.trackable as Plane).trackingState == TrackingState.TRACKING) {
            val addAction = {
                placeNoteNode(
                    context = context,
                    modelLoader = modelLoader,
                    nodes = nodes,
                    hitResult = hitResult,
                    viewAttachmentManager = viewAttachmentManager
                )
            }
            addAction()
            onAddNoteReady(addAction)
        }
    })

    ARScene(
        modifier = Modifier.fillMaxSize(),
        engine = engine,
        modelLoader = modelLoader,
        childNodes = nodes,
        onGestureListener = gestureListener,
        planeRenderer = true,
        onSessionUpdated = { _, frame ->
            val hasPlane = frame.getUpdatedTrackables(Plane::class.java).any { it.trackingState == TrackingState.TRACKING }
            onPlaneTracking(hasPlane)
        },
        onSessionFailed = { e -> Log.e("ARNoteScene", "Session failed", e) }
    )
}

@SuppressLint("SetTextI18n")
private fun placeNoteNode(
    context: Context,
    modelLoader: io.github.sceneview.loaders.ModelLoader,
    nodes: MutableList<Node>,
    hitResult: HitResult?,
    viewAttachmentManager: ViewAttachmentManager
) {
    val safeHit = hitResult ?: return
    val engine = modelLoader.engine
    val anchorNode = HitResultNode(engine, hitTest = { safeHit })

    val labelNode = ViewNode(
        engine = engine,
        modelLoader = modelLoader,
        viewAttachmentManager = viewAttachmentManager
    ).apply {
        position = Position(0.0f, 0.0f, 0.0f)
        loadView(context, android.R.layout.simple_list_item_1) { _, view ->
            (view as? TextView)?.apply {
                text = "Not"
                setBackgroundColor("#FFF9C4".toColorInt())
                setTextColor(android.graphics.Color.BLACK)
            }
        }
    }

    anchorNode.addChildNode(labelNode)
    nodes += anchorNode
}

@Suppress("unused")
@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx: Context ->
            val previewView = PreviewView(ctx)
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            try {
                val cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview
                )
            } catch (exc: Exception) {
                // Ignore demo errors
            }
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}
