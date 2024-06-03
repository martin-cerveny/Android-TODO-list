package cz.cvut.fit.cervem27.tasks.features.category.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.features.category.domain.Url


@Composable
fun CategoryIconColoredBackground(
    iconUrl: Url?,
    backgroundColor: Color?,
    tint: Color = Color.Black
){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .background(
                color = backgroundColor?:Color(0xFF03A9F4), // todo edit
                shape = RoundedCornerShape(14.dp)
            )

    ) {
        CategoryIconMissingBackground(iconUrl = iconUrl, tint = tint)
    }
}


@Composable
fun CategoryIconMissingBackground(
    iconUrl: Url?,
    tint: Color
){
    val iconModifier = Modifier
        .padding(8.dp)
        .fillMaxSize()

    iconUrl?.let { url ->
        SvgImage(
            url = url,
            modifier = iconModifier,
            tint = tint
        )
    }?:run {
        DefaultIcon(modifier = iconModifier, tint = tint)
    }
}


object IconColorsConstants {
    private const val DEFAULT_SATURATION = 0.5f
    private const val DEFAULT_LIGHTNESS = 0.5f

    fun hslColor(hue: Float): Color {
        return Color.hsl(
            hue = hue,
            saturation = DEFAULT_SATURATION,
            lightness = DEFAULT_LIGHTNESS
        )
    }
}


@Composable
fun DefaultIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        painter = painterResource(id = R.drawable.icon_park_outline__check_one),
        contentDescription = stringResource(R.string.default_category_icon),
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun SvgImage(url: Url, modifier: Modifier = Modifier, tint: Color) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = null,
        modifier = modifier,
        colorFilter = ColorFilter.tint(tint)
    )
}