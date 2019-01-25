package report;

import utils.HashUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class IntegrityFile {
    private String path;
    private String hash;
    private Boolean validate;

    public IntegrityFile(String path, String hash){
        this.path = path;
        this.hash = hash;
        this.validate = false;
    }

    public IntegrityFile(Path path, String hash) {
        this(path.toString(), hash);
    }

    public IntegrityFile(Path path) throws IOException, NoSuchAlgorithmException {
        this(path.toString(), HashUtils.generateSha256(path));
    }

    public String getPath(){
        return this.path;
    }

    public String getHash(){
        return this.hash;
    }

    public String getShortHash() {
        return this.getHash().substring(Math.max(this.getHash().length() - 6, 0));
    }

    public Boolean getValidate(){
        return this.validate;
    }

    public boolean check() throws IOException, NoSuchAlgorithmException {
        Path _path = Paths.get(path);
        this.validate = HashUtils.generateSha256(_path).equals(this.hash);
        return this.validate;
    }

}
