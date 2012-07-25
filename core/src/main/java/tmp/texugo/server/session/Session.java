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

import java.util.Set;

import org.xnio.IoFuture;
import tmp.texugo.server.HttpServerExchange;

/**
 * Represents a HTTP session.
 * <p/>
 * Many operations provide both a blocking and an asynchronous version.
 * <p/>
 * When using the async versions of operations no guarantee is made as to which threads will
 * run listeners registered with this session manger. When using the blocking version the listeners are guaranteed
 * to run in the calling thread.
 *
 * @author Stuart Douglas
 */
public interface Session {

    String ATTACHMENT_KEY = "org.texugo.session.Session";

    /**
     * Returns a string containing the unique identifier assigned
     * to this session. The identifier is assigned
     * by the servlet container and is implementation dependent.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return a string specifying the identifier
     * assigned to this session
     */
    String getId();


    /**
     * Called when a request is done with the session.
     *
     * @param serverExchange The http server exchange for this request
     */
    void requestDone(final HttpServerExchange serverExchange);

    /**
     * Returns the time when this session was created, measured
     * in milliseconds since midnight January 1, 1970 GMT.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return a <code>long</code> specifying
     * when this session was created,
     * expressed in
     * milliseconds since 1/1/1970 GMT
     */
    long getCreationTime();

    /**
     * Returns the last time the client sent a request associated with
     * this session, as the number of milliseconds since midnight
     * January 1, 1970 GMT, and marked by the time the container received the request.
     * <p/>
     * <p>Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access
     * time.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return a <code>long</code>
     * representing the last time
     * the client sent a request associated
     * with this session, expressed in
     * milliseconds since 1/1/1970 GMT
     */
    long getLastAccessedTime();


    /**
     * Specifies the time, in seconds, between client requests before the
     * servlet container will invalidate this session.  A negative time
     * indicates the session should never timeout.
     *
     * @param interval An integer specifying the number
     *                 of seconds
     */
    void setMaxInactiveInterval(int interval);

    /**
     * Returns the maximum time interval, in seconds, that
     * the servlet container will keep this session open between
     * client accesses. After this interval, the servlet container
     * will invalidate the session.  The maximum time interval can be set
     * with the <code>setMaxInactiveInterval</code> method.
     * A negative time indicates the session should never timeout.
     *
     * @return an integer specifying the number of
     * seconds this session remains open
     * between client requests
     * @see        #setMaxInactiveInterval
     */
    int getMaxInactiveInterval();

    /**
     * Returns the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound under the name.
     *
     * @param name a string specifying the name of the object
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return the object with the specified name
     */
    Object getAttribute(String name);

    /**
     * async version of {@link #getAttribute(String)}
     */
    IoFuture<Object> getAttributeAsync(String name);

    /**
     * Returns an <code>Set</code> of <code>String</code> objects
     * containing the names of all the objects bound to this session.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return an <code>Set</code> of
     * <code>String</code> objects specifying the
     * names of all the objects bound to
     * this session
     */
    Set<String> getAttributeNames();

    /**
     * async version of {@link #getAttributeNames()}
     */
    IoFuture<Set<String>> getAttributeNamesAsync();

    /**
     * Binds an object to this session, using the name specified.
     * If an object of the same name is already bound to the session,
     * the object is replaced.
     * <p/>
     * <p/>
     * <p/>
     * <p>If the value passed in is null, this has the same effect as calling
     * <code>removeAttribute()<code>.
     *
     * @param name  the name to which the object is bound;
     *              cannot be null
     * @param value the object to be bound
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    void setAttribute(String name, Object value);

    /**
     * async version of {@link #setAttribute(String, Object)}
     */
    IoFuture<Void> setAttributeAsync(final String name, Object value);

    /**
     * Removes the object bound with the specified name from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * @param name the name of the object to remove from this session
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    void removeAttribute(final String name);

    /**
     * async version of {@link #removeAttribute(String)}
     */
    IoFuture<Void> removeAttributeAsync(final String name);

    /**
     * Invalidates this session then unbinds any objects bound
     * to it.
     *
     * TODO: how do we handle clearing the session cookie, it seems messy to rely on the session to do it
     *
     * @throws IllegalStateException if this method is called on an
     *                               already invalidated session
     */
    void invalidate(final HttpServerExchange exchange);

    /**
     * Async version of {@link #invalidate(HttpServerExchange)}
     *
     * @return
     */
    IoFuture<Void> invalidateAsync(final HttpServerExchange exchange);

    /**
     * @return The session manager that is associated with this session
     */
    SessionManager getSessionManager();
}
