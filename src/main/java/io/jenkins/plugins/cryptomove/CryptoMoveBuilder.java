package io.jenkins.plugins.cryptomove;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.lang.ProcessBuilder;
import java.lang.Process;
import java.util.Map;
import java.nio.charset.Charset;

import jenkins.tasks.SimpleBuildStep;

import org.jenkinsci.Symbol;

public class CryptoMoveBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private final String token;

    @DataBoundConstructor
    public CryptoMoveBuilder(String name, String token) {
        this.name = name;
        this.token = token;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
        URL url = new URL("https://api.cryptomove.com/v1/user/secret/list_no_dup");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorizaton", token);
        con.setRequestProperty("Content-Type", "application/json");
        int status = con.getResponseCode();
        if (status < 300) {
            InputStream inputStream = con.getInputStream();
            String body = IOUtils.toString(inputStream, "UTF-8");
            listener.getLogger().println("You have the keys " + body);

            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", name);
            Map<String, String> env = pb.environment();
            env.put("CRYPTOMOVE", "vault");
            Process p = pb.start();

            String stderr = IOUtils.toString(p.getErrorStream(), Charset.defaultCharset());
            String stdout = IOUtils.toString(p.getInputStream(), Charset.defaultCharset());

            listener.getLogger().println("Standard Error " + stderr);
            listener.getLogger().println("Standard Out " + stdout);

            listener.getLogger().println("You are running the command: " + name);
            listener.getLogger().println("You are using the token: " + token);
        } else {
            InputStream inputStream = con.getErrorStream();
            String body = IOUtils.toString(inputStream, "UTF-8");
            listener.getLogger().println("there was an error with your request " + body);
            throw new RuntimeException("there was an error in the request");
        }
    }

    @Symbol("cryptomove")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.CryptoMoveBuilder_DescriptorImpl_errors_missingName());
            if (value.length() < 4)
                return FormValidation.warning(Messages.CryptoMoveBuilder_DescriptorImpl_warnings_tooShort());
            return FormValidation.ok();
        }

        public FormValidation doCheckToken(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.CryptoMoveBuilder_DescriptorImpl_errors_missingToken());
            if (value.length() < 4)
                return FormValidation.warning(Messages.CryptoMoveBuilder_DescriptorImpl_warnings_tooShort());
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.CryptoMoveBuilder_DescriptorImpl_DisplayName();
        }

    }

}
