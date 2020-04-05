import org.jfrog.artifactory.client.ArtifactoryClientBuilder
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl
import spock.lang.Specification

class setPropertyValueForAllRepositories extends Specification {
    def 'delete by property value test'() {
        setup:
        def baseurl = 'http://34.66.8.2:8082/artifactory'
        def artifactory = ArtifactoryClientBuilder.create().setUrl(baseurl).setUsername('admin').setPassword('3amn!6dH').build()

        def builder = artifactory.repositories().builders().localRepositoryBuilder()
        builder.key('cleanup-local')
        builder.repositorySettings(new GenericRepositorySettingsImpl())
        artifactory.repositories().create(0, builder.build())
        def repo = artifactory.repository('cleanup-local')

        def somefile = new ByteArrayInputStream('content'.bytes)
        def otherfile = new ByteArrayInputStream('content'.bytes)
        repo.upload('somefile', somefile).withProperty('artifact.maturity', 'production').doUpload()
        repo.upload('otherfile', otherfile).withProperty('someprop', '6').doUpload()

        def plugin = artifactory.plugins().execute('setPropertyValuesForAllRepositories')
        plugin.withParameter('propertyName', 'artifact.maturity')
        plugin.withParameter('propertyValue', 'production')
        plugin.withParameter('repo', 'libs-release').sync()

        when:
        property = repo.file('somefile').getProperties().get("artifact.maturity")

        then:
        property != null

        cleanup:
        repo?.delete()
    }

//    def 'delete by property value dryRun test'() {
//
//        setup:
//        def baseurl = 'http://localhost:8088/artifactory'
//        def artifactory = ArtifactoryClientBuilder.create().setUrl(baseurl)
//            .setUsername('admin').setPassword('password').build()
//        def builder = artifactory.repositories().builders().localRepositoryBuilder()
//        builder.key('cleanup-local')
//        builder.repositorySettings(new GenericRepositorySettingsImpl())
//        artifactory.repositories().create(0, builder.build())
//        def repo = artifactory.repository('cleanup-local')
//        def somefile = new ByteArrayInputStream('content'.bytes)
//        repo.upload('somefile', somefile).withProperty('someprop', '4').doUpload()
//        def plugin = artifactory.plugins().execute('setPropertyValuesForAllRepositories')
//        plugin.withParameter('propertyName', 'someprop')
//        plugin.withParameter('propertyValue', '5')
//        plugin.withParameter('repo', 'cleanup-local')
//        plugin.withParameter('dryRun', 'true').sync()
//
//        when:
//        def fileInfo = repo.file('somefile').info()
//
//        then:
//        fileInfo != null
//
//        cleanup:
//        repo?.delete()
//    }
}
