package fd.ng.web.fileupload.util;

import fd.ng.web.fileupload.FileItemHeaders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Default implementation of the {@link FileItemHeaders} interface.
 *
 */
public class FileItemHeadersImpl implements FileItemHeaders, Serializable {

    /**
     * Serial version UID, being used, if serialized.
     */
    private static final long serialVersionUID = -4455695752627032559L;

    /**
     * Map of <code>String</code> keys to a <code>List</code> of
     * <code>String</code> instances.
     */
    private final Map<String, List<String>> headerNameToValueListMap = new LinkedHashMap<String, List<String>>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader(String name) {
        String nameLower = name.toLowerCase(Locale.ENGLISH);
        List<String> headerValueList = headerNameToValueListMap.get(nameLower);
        if (null == headerValueList) {
            return null;
        }
        return headerValueList.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<String> getHeaderNames() {
        return headerNameToValueListMap.keySet().iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<String> getHeaders(String name) {
        String nameLower = name.toLowerCase(Locale.ENGLISH);
        List<String> headerValueList = headerNameToValueListMap.get(nameLower);
        if (null == headerValueList) {
            headerValueList = Collections.emptyList();
        }
        return headerValueList.iterator();
    }

    /**
     * Method to add header values to this instance.
     *
     * @param name name of this header
     * @param value value of this header
     */
    public synchronized void addHeader(String name, String value) {
        String nameLower = name.toLowerCase(Locale.ENGLISH);
        List<String> headerValueList = headerNameToValueListMap.get(nameLower);
        if (null == headerValueList) {
            headerValueList = new ArrayList<String>();
            headerNameToValueListMap.put(nameLower, headerValueList);
        }
        headerValueList.add(value);
    }

}
