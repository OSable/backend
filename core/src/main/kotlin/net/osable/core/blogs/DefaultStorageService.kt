package net.osable.core.blogs

import com.vladsch.flexmark.ext.autolink.AutolinkExtension
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataSet
import org.apache.logging.log4j.spi.CopyOnWrite
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.StandardCopyOption

@Service
class DefaultStorageService : StorageService {

    companion object {
        private val options = MutableDataSet().apply {
            set(Parser.EXTENSIONS, listOf(StrikethroughExtension.create(), AutolinkExtension.create()))
        }
        private val parser = Parser.builder(options).build()
        private val renderer = HtmlRenderer.builder(options).build()
    }

    init {
        val file = File("./blogs")
        if (! file.exists()) { file.mkdir() }
    }

    override fun save(file: MultipartFile) {
        file.originalFilename ?: return
        Files.copy(file.inputStream, File("./blogs/${file.originalFilename}").toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

    @Cacheable("blog")
    override fun load(filename: String): String? {
        val file = File("./blogs/$filename")
        if (! file.exists()) return null
        return renderMarkdownAsHTML(file.readText())
    }

    private fun renderMarkdownAsHTML(markdown: String) = renderer.render(parser.parse(markdown))

}