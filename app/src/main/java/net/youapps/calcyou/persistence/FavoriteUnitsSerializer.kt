package net.youapps.calcyou.persistence

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import net.you_apps.calcyou.FavoriteUnits
import java.io.InputStream
import java.io.OutputStream

object FavoriteUnitsSerializer : Serializer<FavoriteUnits> {
    override val defaultValue: FavoriteUnits = FavoriteUnits.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FavoriteUnits {
        try {
            return FavoriteUnits.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: FavoriteUnits, output: OutputStream) {
        return t.writeTo(output)
    }
}

val Context.favoriteUnitsDataStore: DataStore<FavoriteUnits> by dataStore(
    fileName = "favorite_units.pb",
    serializer = FavoriteUnitsSerializer
)