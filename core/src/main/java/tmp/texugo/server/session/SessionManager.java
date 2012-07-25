/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tmp.texugo.server.session;

import org.xnio.IoFuture;
import tmp.texugo.server.HttpServerExchange;

/**
 * Interface that manages sessions.
 *
 * The session manager is responsible for maintaining session state.
 *
 * As part of session creation the session manager MUST attempt to retrieve the {@link SessionCookieConfig} from
 * the {@link HttpServerExchange} and use it to set the session cookie. The frees up the session manager from
 * needing to know details of the cookie configuration. When invalidating a session the session manager MUST
 * also use this to clear the session cookie.
 *
 *
 * @author Stuart Douglas
 */
public interface SessionManager {

    String ATTACHMENT_KEY = "org.texugo.session.SessionManager";

    /**
     * Creates a new session. Any {@link SessionListener}s registered with this manager will be notified
     * of the session creation.
     *
     *
     * @return The created session
     */
    IoFuture<Session> createSessionAsync(final HttpServerExchange serverExchange);

    /**
     * Creates a new session. Any {@link SessionListener}s registered with this manager will be notified
     * of the session creation.
     *
     *
     * @return The created session
     */
    Session createSession(final HttpServerExchange serverExchange);

    /**
     *
     * @param sessionId The session id
     * @return An IoFuture that can be used to retrieve the session, or an IoFuture that will return null if not found
     */
    IoFuture<Session> getSessionAsync(final HttpServerExchange serverExchange, final String sessionId);

    /**
     *
     * @param sessionId The session id
     * @return The session, or null
     */
    Session getSession(final HttpServerExchange serverExchange, final String sessionId);

    /**
     * Registers a session listener for the session manager
     *
     * @param listener The listener
     */
    void registerSessionListener(final SessionListener listener);

    /**
     * Removes a session listener from the session manager
     * @param listener the listener
     */
    void removeSessionListener(final SessionListener listener);

    /**
     * Sets the defaul session timeout
     * @param timeout the timeout
     */
    void setDefaultSessionTimeout(final int timeout);

}
