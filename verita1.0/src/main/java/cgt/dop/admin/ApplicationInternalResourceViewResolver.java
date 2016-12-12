package cgt.dop.admin;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class ApplicationInternalResourceViewResolver  extends InternalResourceViewResolver{


    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        String url = getPrefix() + viewName + getSuffix();
        InputStream stream = getServletContext().getResourceAsStream(url);
        if (stream == null) {
            return new NonExistentView();
        } else {
            stream.close();
        }
        return super.buildView(viewName);
    }

    
    private static class NonExistentView extends AbstractUrlBasedView {

        protected boolean isUrlRequired() {
            return false;
        }

        public boolean checkResource(Locale locale) throws Exception {
            return false;
        }

        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                HttpServletResponse response) throws Exception {
            // Purposely empty, it should never get called
        }
    }
}
