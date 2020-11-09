package ir.malv.cleanmovies.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CMScope : CoroutineScope {
    override val coroutineContext: CoroutineContext = Job()
}

fun cmScope(op: suspend CoroutineScope.()->Unit): Job = CMScope().launch(block = op)