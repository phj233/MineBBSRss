package info.phj233

import info.phj233.listener.RssListener
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.utils.info

object PluginMain : KotlinPlugin(

    JvmPluginDescription(
        id = "info.phj233.minebbsrss",
        name = "MineBBSRSS",
        version = "0.2.0"
    ) {
        author("phj233")
        info(
            """
            发送MineBBS的RSS订阅
        """.trimIndent()
        )
    }
) {
    override fun onEnable() {
        Config.reload()
        GlobalEventChannel.subscribeAlways<BotOnlineEvent> {e->
            try {
                if (!Config.enable) {
                    logger.info { "插件未启用" }
                }
                RssListener.INSTANCE.start(e.bot)
                logger.info { "MineBBSRSS loaded ！" }
            }catch (e:Exception){
                logger.info { e.message }
            }
        }
    }
}
