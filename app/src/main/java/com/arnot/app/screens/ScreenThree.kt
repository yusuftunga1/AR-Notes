package com.arnot.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.arnot.app.ui.theme.*

data class Note(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val height: Int
)

@Composable
fun ScreenThree(onBack: () -> Unit) {
    val notes = listOf(
        Note(1, "Dağ Manzarası", "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400", 200),
        Note(2, "Proje Notları", "https://images.unsplash.com/photo-1484480974693-6ca0a78fb36b?w=400", 150),
        Note(3, "Kahve Molası", "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400", 250),
        Note(4, "Kitap Notları", "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=400", 180),
        Note(5, "Workspace", "https://images.unsplash.com/photo-1484480974693-6ca0a78fb36b?w=400", 220),
        Note(6, "Doğa", "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=400", 200),
        Note(7, "Gün Batımı", "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?w=400", 180),
        Note(8, "Kahve & Kitap", "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400", 240),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Arka plan
        Image(
            painter = rememberAsyncImagePainter(
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=1080"
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.6f))
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(48.dp)
                        .background(GlassWhite, CircleShape)
                ) {
                    Icon(Icons.Default.ChevronLeft, null, tint = Color.White)
                }

                Text(
                    "Notlar Galerisi",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // Boş alan (dengeli görünüm için)
                Spacer(modifier = Modifier.width(48.dp))
            }

            // Masonry Grid
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalItemSpacing = 12.dp
            ) {
                items(notes) { note ->
                    NoteCard(note)
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: Note) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(note.height.dp)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        color = GlassWhite
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(note.imageUrl),
                contentDescription = note.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(0.7f)
                            )
                        )
                    )
            )

            Text(
                text = note.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}

