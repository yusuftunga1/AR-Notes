package com.arnot.app.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.arnot.app.ui.theme.*

@Composable
fun ScreenOne(onNavigate: () -> Unit) {
    var isSettingsOpen by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val settingsOffset by animateDpAsState(
        targetValue = if (isSettingsOpen) 0.dp else (-280).dp,
        animationSpec = tween(300), label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(
                "https://images.unsplash.com/photo-1605702012553-e954fbde66eb?w=1080"
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0x66172554),
                            Color(0x990F172A),
                            Color(0xCC0F172A)
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("9:40 PM", color = Color.White, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            IconButton(
                onClick = { isSettingsOpen = !isSettingsOpen },
                modifier = Modifier
                    .size(48.dp)
                    .background(GlassWhite, CircleShape)
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Ayarlar",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Arnot'a\nhoş geldiniz",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    lineHeight = 44.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("E-posta adresiniz") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, null, tint = Color.White.copy(0.7f))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GlassWhite, RoundedCornerShape(16.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = YellowPrimary,
                        unfocusedBorderColor = Color.White.copy(0.3f),
                        cursorColor = YellowPrimary,
                        focusedPlaceholderColor = Color.White.copy(0.6f),
                        unfocusedPlaceholderColor = Color.White.copy(0.6f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Şifreniz") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, null, tint = Color.White.copy(0.7f))
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                if (showPassword) Icons.Default.VisibilityOff
                                else Icons.Default.Visibility,
                                null,
                                tint = Color.White.copy(0.7f)
                            )
                        }
                    },
                    visualTransformation = if (showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GlassWhite, RoundedCornerShape(16.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = YellowPrimary,
                        unfocusedBorderColor = Color.White.copy(0.3f),
                        cursorColor = YellowPrimary,
                        focusedPlaceholderColor = Color.White.copy(0.6f),
                        unfocusedPlaceholderColor = Color.White.copy(0.6f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Şifremi unuttum?",
                    color = Color.White.copy(0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowPrimary
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "Giriş yap",
                    color = Slate800,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Hesabınız yok mu? ", color = Color.White.copy(0.7f), fontSize = 14.sp)
                Text(
                    "Kayıt olun",
                    color = YellowPrimary,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .offset(x = settingsOffset)
                .background(GlassWhiteMedium.copy(0.95f))
                .padding(24.dp)
        ) {
            SettingsMenu(onClose = { isSettingsOpen = false })
        }

        if (isSettingsOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.3f))
                    .clickable { isSettingsOpen = false }
            )
        }
    }
}

@Composable
fun SettingsMenu(onClose: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Ayarlar",
                fontSize = 24.sp,
                color = Slate800,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(32.dp)
                    .background(Slate200, CircleShape)
            ) {
                Icon(Icons.Default.Close, null, tint = Slate800)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val settings = listOf(
            Triple(Icons.Default.Notifications, "Bildirimler", true),
            Triple(Icons.Default.DarkMode, "Koyu tema", true),
            Triple(Icons.Default.Language, "Dil", false),
            Triple(Icons.Default.Person, "Hesap", false),
            Triple(Icons.Default.Lock, "Gizlilik", false),
            Triple(Icons.Default.Help, "Yardım ve destek", false),
            Triple(Icons.Default.Info, "Hakkında", false)
        )

        settings.forEach { (icon, label, hasToggle) ->
            SettingItem(icon = icon, label = label, hasToggle = hasToggle)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            "Arnot v1.0.0",
            color = Slate400,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    hasToggle: Boolean
) {
    var isEnabled by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = YellowPrimary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = Slate800, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                label,
                color = Slate800,
                modifier = Modifier.weight(1f)
            )

            if (hasToggle) {
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { isEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = YellowPrimary,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Slate400
                    )
                )
            }
        }
    }
}
