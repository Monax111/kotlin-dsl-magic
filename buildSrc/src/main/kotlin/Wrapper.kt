import org.gradle.api.Action
import org.gradle.api.Task

inline fun <reified T : Task> T.doOnFail(crossinline block: T.() -> Unit) {
    val originalAction = actions.first()
    val wrapper = Action<Task> {
        try {
            originalAction.execute(this)
        } finally {
            block()
        }
    }
    actions.remove(originalAction)
    actions.add(wrapper)
}