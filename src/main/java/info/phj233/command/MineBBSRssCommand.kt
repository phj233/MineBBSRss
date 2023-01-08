package info.phj233.command

import info.phj233.Config
import info.phj233.PluginMain
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.getGroupOrNull

object MineBBSRssCommand : CompositeCommand(
    owner = PluginMain,
    "minebbsrss",
    description = "MineBBSRss 指令"
) {
    @SubCommand
    suspend fun list(sender: CommandSender) {
        val helpText = """
            |MineBBSRss 指令列表:
            |  /minebbsrss list - 查看指令列表
            |  /minebbsrss enable <true|false> - 启用|关闭插件
            |  /minebbsrss admin <add|remove> <qq> - 添加|移除管理员
            |  /minebbsrss group <add|remove> <group> - 添加|移除群
            |  /minebbsrss url <url> - 设置rss链接
            """.trimMargin()
        sender.sendMessage(helpText)
    }

    @SubCommand
    suspend fun enable(sender: CommandSender, enable: Boolean) {
        Config.enable = enable
        sender.sendMessage("插件已${if (enable) "启用" else "禁用"}")
    }

    @SubCommand
    suspend fun admin(sender: CommandSender, action: String, qq: Long) {
        when (action) {
            "add" -> {
                Config.addAdmin(qq)
                sender.sendMessage("管理员<$qq>添加成功")
            }
            "remove" -> {
                Config.removeAdmin(qq)
                sender.sendMessage("管理员<$qq>移除成功")
            }
            else -> sender.sendMessage("未知操作, 请使用 /minebbsrss admin <add|remove> <qq>")
        }
    }

    @SubCommand
    suspend fun group(sender: CommandSender, action: String, group: Long) {
        when (action) {
            "add" -> {
                if (group in Config.groups) {
                    sender.sendMessage("群<$group>已存在")
                    return
                }
                Config.addGroup(group)
                sender.sendMessage("群<$group>添加成功")
            }
            "remove" -> {
                if (group in Config.groups) {
                    Config.removeGroup(group)
                    sender.sendMessage("群<$group>移除成功")
                } else {
                    sender.sendMessage("群<$group>不在允许列表中")
                }
            }
            else -> sender.sendMessage("未知操作, 请使用 /minebbsrss group <add|remove> <group>")
        }
    }

    @SubCommand
    suspend fun group(sender: CommandSender, action: String){
        val group = sender.getGroupOrNull()
        if (group == null) {
            sender.sendMessage("请在聊天环境使用")
            return
        }
        when (action) {
            "add" -> {
                if (group.id in Config.groups) {
                    sender.sendMessage("群<${group.id}>已存在")
                    return
                }
                Config.addGroup(group.id)
                sender.sendMessage("群<${group.id}>添加成功")
            }
            "remove" -> {
                if (group.id in Config.groups) {
                    Config.removeGroup(group.id)
                    sender.sendMessage("群<${group.id}>移除成功")
                } else {
                    sender.sendMessage("群<${group.id}>不在允许列表中")
                }
            }
            else -> sender.sendMessage("未知操作, 请使用 /minebbsrss group <add|remove>")
        }
    }

    @SubCommand
    suspend fun url(sender: CommandSender, url: String) {
        Config.url = url
        sender.sendMessage("rss链接:<$url>,设置成功")
    }



}
