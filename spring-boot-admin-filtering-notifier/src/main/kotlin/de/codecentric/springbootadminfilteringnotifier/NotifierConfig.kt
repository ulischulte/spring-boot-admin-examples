package de.codecentric.springbootadminfilteringnotifier

import de.codecentric.boot.admin.server.domain.entities.Instance
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository
import de.codecentric.boot.admin.server.domain.events.InstanceEvent
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent
import de.codecentric.boot.admin.server.notify.CompositeNotifier
import de.codecentric.boot.admin.server.notify.MailNotifier
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier
import de.codecentric.boot.admin.server.notify.filter.NotificationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration(proxyBeanMethods = false)
class NotifierConfig(
    private val repository: InstanceRepository,
    private val mailNotifier: MailNotifier
) {

    @Bean
    fun filteringNotifier(): FilteringNotifier {
        val delegate = CompositeNotifier(listOf(mailNotifier))
        val filteringNotifier = FilteringNotifier(delegate, repository)
        filteringNotifier.addFilter(CustomNotificationFilter())
        return filteringNotifier
    }

    internal class CustomNotificationFilter : NotificationFilter {
        override fun getId(): String {
            return CustomNotificationFilter::class.java.simpleName
        }

        override fun filter(event: InstanceEvent, instance: Instance): Boolean {
            // filter out undesired events
            return event.type == InstanceRegisteredEvent.TYPE
        }
    }
}
