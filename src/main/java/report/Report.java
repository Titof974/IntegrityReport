package report;

import utils.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class Report {

    private transient String path;
    private String includeRegex;
    private String excludeRegex;
    private Date createdAt;
    private List<IntegrityFile> files;

    public Report(String path, String includeRegex, String excludeRegex, Date createdAt) {
        this.path = path;
        this.includeRegex = includeRegex;
        this.excludeRegex = excludeRegex;
        this.createdAt = (createdAt == null) ? new Date() : createdAt;
    }

    public Report(String path, String includeRegex, String excludeRegex){
        this(path, includeRegex, excludeRegex, null);
    }

    public Report setPath(String path) {
        this.path = path;
        return this;
    }

    public void generate() throws IOException, NoSuchAlgorithmException {
        this.files = new ArrayList<>();
        List<Path> paths = FileUtils.listAllInDirectory(this.path,
                Optional.ofNullable(this.includeRegex),
                Optional.ofNullable(this.excludeRegex));
        for (Path pathFound : paths) {
            files.add(new IntegrityFile(pathFound));
        }
    }

    public void checkFiles() throws IOException, NoSuchAlgorithmException {
        for (IntegrityFile fileValidator : this.files) {
            fileValidator.check();
        }
    }

    public boolean isValid() {
        return this.files.stream().allMatch(file -> file.getValidate());
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(String.format("Created at : %s\n", this.createdAt));
        string.append(String.format("IncludeRegex: %s\n", (this.includeRegex == null) ? "" : this.includeRegex));
        string.append(String.format("ExcludeRegex: %s\n", (this.excludeRegex == null) ? "" :this.excludeRegex));
        string.append(String.format("Files:\n"));
        for (IntegrityFile fileValidator : this.files) {
            string.append(String.format("%s - %s\n", fileValidator.getPath(), fileValidator.getHash()));
        }
        return string.toString();
    }

    public String listFiles() {
        StringBuilder string = new StringBuilder();
        for (IntegrityFile file : this.files) {
            string.append(String.format("%s - %s\n", file.getPath(), file.getHash()));
        }
        return string.toString();
    }

    public String toCSV() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Created at : %s\n", this.createdAt));
        stringBuilder.append("\n");
        stringBuilder.append("File, Version, Full Hash\n");
        this.getFiles().stream().forEach(f -> stringBuilder.append(String.format("%s, %s, %s\n", f.getPath(), f.getShortHash(), f.getHash())));
        return stringBuilder.toString();
    }

    public List<IntegrityFile> getFiles(){
        return this.files;
    }


    public Date getCreatedAt(){
        return this.createdAt;
    }

    public String getIncludeRegex(){
        return this.includeRegex;
    }

    public String getExcludeRegex(){
        return this.excludeRegex;
    }

    public void saveAsCSV(String path) {
        FileUtils.saveStringInFile(this.toCSV(), path);
    }

}
