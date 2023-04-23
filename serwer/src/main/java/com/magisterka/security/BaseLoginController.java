/*
 * Copyright 2017-2023 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.magisterka.security;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.Authenticator;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.handlers.LoginHandler;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.magisterka.security.LoginSource.REQUEST_ATTRIBUTE;

@Secured(SecurityRule.IS_ANONYMOUS)
public abstract class BaseLoginController {
    @Inject
    private Authenticator authenticator;
    @Inject
    private LoginHandler loginHandler;

    @Post
    @SingleResult
    public Publisher<MutableHttpResponse<?>> login(@NotNull @Valid @Body LoginRequest loginRequest, HttpRequest<?> request) {
        request.setAttribute(REQUEST_ATTRIBUTE, loginSource());
        AuthenticationRequest<String, String> authenticationRequest = new UsernamePasswordCredentials(
                loginRequest.getEmail(), loginRequest.getPassword());
        return Flux.from(authenticator.authenticate(request, authenticationRequest))
                .map(authenticationResponse -> {
                    if (authenticationResponse.isAuthenticated() && authenticationResponse.getAuthentication().isPresent()) {
                        Authentication authentication = authenticationResponse.getAuthentication().get();
                        return loginHandler.loginSuccess(authentication, request);
                    } else {
                        return loginHandler.loginFailed(authenticationResponse, request);
                    }
                }).switchIfEmpty(Mono.defer(() -> Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED))));
    }

    protected abstract LoginSource loginSource();
}
