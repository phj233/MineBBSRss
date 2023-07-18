package info.phj233

import info.phj233.command.MineBBSRssCommand
import info.phj233.quartz.MineBBSRss
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.utils.info

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "info.phj233.minebbsrss",
        name = "MineBBSRss",
        version = "0.9.0"
    ) {
        author("phj233")
        info(
            """
            发送MineBBS新发布的RSS订阅
        """.trimIndent()
        )
    }
) {

    lateinit var botInstance: Bot

    override fun onEnable() {
        //加载配置
        Config.reload()
        //注册指令
        CommandManager.registerCommand(MineBBSRssCommand)
        GlobalEventChannel.subscribeAlways<BotOnlineEvent> {e->
                if (!Config.enable) {
                    logger.info { "插件未启用" }
                }
                botInstance = e.bot
                //启动定时任务
                MineBBSRss.start(e.bot)
                logger.info { "MineBBSRss loaded ！" }
        }
    }
}
