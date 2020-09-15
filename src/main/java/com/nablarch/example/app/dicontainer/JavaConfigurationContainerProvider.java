package com.nablarch.example.app.dicontainer;

import com.nablarch.example.app.web.action.*;
import com.nablarch.example.app.web.common.authentication.context.LoginUserPrincipal;
import com.nablarch.example.app.web.form.*;
import nablarch.core.log.Logger;
import nablarch.core.log.LoggerManager;
import nablarch.core.repository.initialization.Initializable;
import nablarch.fw.dicontainer.Container;
import nablarch.fw.dicontainer.annotation.AnnotationContainerBuilder;
import nablarch.fw.dicontainer.annotation.AnnotationScopeDecider;
import nablarch.fw.dicontainer.exception.ContainerCreationException;
import nablarch.fw.dicontainer.exception.ContainerException;
import nablarch.fw.dicontainer.nablarch.Containers;
import nablarch.fw.dicontainer.scope.ScopeDecider;
import nablarch.fw.dicontainer.web.RequestScoped;
import nablarch.fw.dicontainer.web.SessionScoped;
import nablarch.fw.dicontainer.web.context.RequestContextSupplier;
import nablarch.fw.dicontainer.web.context.SessionContextSupplier;
import nablarch.fw.dicontainer.web.scope.RequestScope;
import nablarch.fw.dicontainer.web.scope.SessionScope;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JavaConfigurationContainerProvider implements Initializable {
    private static final Logger LOGGER = LoggerManager.get(JavaConfigurationContainerProvider.class);

    private RequestContextSupplier requestContextSupplier;
    private SessionContextSupplier sessionContextSupplier;
    private boolean eagerLoad;

    @Override
    public void initialize() {
        AnnotationContainerBuilder annotationContainerBuilder = createBuilder();
        COMPONENTS.stream().forEach(clazz -> annotationContainerBuilder.register(clazz));
        try {
            final Container container = annotationContainerBuilder.build();
            Containers.set(container);
        } catch (final ContainerCreationException e) {
            LOGGER.logDebug("Container Creation failed. see following messages for detail.");
            for (final ContainerException ce : e.getExceptions()) {
                LOGGER.logDebug(ce.getMessage());
            }
            throw e;
        }
    }

    private AnnotationContainerBuilder createBuilder() {
        final ScopeDecider scopeDecider = AnnotationScopeDecider.builder()
                .addScope(RequestScoped.class, new RequestScope(requestContextSupplier))
                .addScope(SessionScoped.class, new SessionScope(sessionContextSupplier))
                .eagerLoad(eagerLoad)
                .build();
        return AnnotationContainerBuilder.builder()
                .scopeDecider(scopeDecider)
                .build();
    }

    /**
     *  シングルトンコンポーネントのイーガーロードを行うかを設定する。
     *
     * @param eagerLoad イーガーロードを行う場合、真
     */
    public void setEagerLoad(final boolean eagerLoad) {
        this.eagerLoad = eagerLoad;
    }

    /**
     * リクエストコンテキスト取得クラスを設定する。
     *
     * @param requestContextSupplier  リクエストコンテキスト取得クラス
     */
    public void setRequestContextSupplier(final RequestContextSupplier requestContextSupplier) {
        this.requestContextSupplier = requestContextSupplier;
    }

    /**
     * セッションコンテキスト取得クラスを設定する。
     * @param sessionContextSupplier セッションコンテキスト取得クラス
     */
    public void setSessionContextSupplier(final SessionContextSupplier sessionContextSupplier) {
        this.sessionContextSupplier = sessionContextSupplier;
    }

    /**
     * コンポーネント登録するクラスを定義。
     */
    private static final Set<Class<?>> COMPONENTS = new HashSet<>(Arrays.asList(
            AuthenticationAction.class,
            ClientAction.class,
            IndustryAction.class,
            ProjectAction.class,
            ProjectBulkAction.class,
            ProjectUploadAction.class,
            ClientSearchForm.class,
            InnerProjectForm.class,
            LoginForm.class,
            ProjectBulkForm.class,
            ProjectForm.class,
            ProjectSearchForm.class,
            ProjectTargetForm.class,
            ProjectUpdateForm.class,
            LoginUserPrincipal.class
    ));

}
