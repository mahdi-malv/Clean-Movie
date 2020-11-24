package ir.malv.cleanmovies.data.store

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.createDataStore
import com.google.protobuf.InvalidProtocolBufferException
import ir.malv.cleanmovies.data.Constants
import ir.malv.cleanmovies.data.gen.UserStore
import java.io.InputStream
import java.io.OutputStream

object UserSerializer : Serializer<UserStore> {
    override val defaultValue: UserStore = UserStore.getDefaultInstance()

    override fun readFrom(input: InputStream): UserStore {
        try {
            return UserStore.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Can not read from proto", e)
        }
    }

    override fun writeTo(t: UserStore, output: OutputStream) = t.writeTo(output)

    /**
     * Get the datastore needed to interact using a builtin function
     */
    fun get(context: Context): DataStore<UserStore> {
        return context.createDataStore(
            Constants.Store.NAME,
            serializer = this
        )
    }

}