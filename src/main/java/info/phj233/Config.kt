package info.phj233

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value
object Config : AutoSavePluginConfig("config") {

    @ValueName("enable")
    @ValueDescription("是否启用")
    var enable by value<Boolean>(true)

    @ValueName("admin")
    @ValueDescription("管理员")
    var admins: MutableSet<Long> by value(mutableSetOf(2780990934,1234567890))

    @ValueName("url")
    @ValueDescription("rss链接")
    var url: String by value("https://www.minebbs.com/forums/-/index.rss")

    @ValueName("groups")
    @ValueDescription("插件允许的群")
    var groups: MutableSet<Long> by value(mutableSetOf(261982336,260872089))

    fun addAdmin(admin: Long) {
        for (id in admins) if (id == admin) return
        admins.add(admin)
    }

    fun removeAdmin(admin: Long) {
        admins.remove(admin)
    }

    fun addGroup(group: Long) {
        for (id in groups) if (id == group) return
        groups.add(group)
    }

    fun removeGroup(group: Long) {
        groups.remove(group)
    }

}