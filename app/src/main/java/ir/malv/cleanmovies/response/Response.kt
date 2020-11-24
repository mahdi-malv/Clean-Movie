package ir.malv.cleanmovies.response

sealed class Response<T> {

    /**
     * IDLE is the initial state. When a response is IDLE,
     *  it means that it's not modified so any ui or ... depending on this state can
     *  be at their own initial state
     */
    class IDLE<T> : Response<T>()

    /**
     * Right before a long running task that is about to assign
     *   results to the state, state must be marked LOADING. So that any ui depending
     *   on this state can perform a loading state (Show progress or etc)
     */
    class LOADING<T>: Response<T>()

    /**
     * When the state has been successfully modified by the long running task,
     * it should be marked SUCCESS and the data should be passed to it's arg.
     * Any waiting ui (on LOADING state) will be notified of the state and can use the data by
     * smart-cast feature of kotlin
     */
    class SUCCESS<T>(val data: T): Response<T>()

    /**
     * If for any reason the assignment failed to be done, the LOADING state must be changed to FAIL.
     * So that any LOADING ui can be notified of the failure and show error ui
     */
    class FAIL<T, R>(val error: R, val code: Int = -1): Response<T>() where R : Throwable

    /**
     * No idea why I added this in the first place.
     * It's mostly useful for shared states between multiple composable
     */
    class EMPTY<T> : Response<T>()
}