// package com.empower.aspect;

// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ResponseStatusException;

// import com.empower.annotation.RolesAllowed;
// import com.empower.config.JWTService;

// import io.jsonwebtoken.Claims;

// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;
// import jakarta.servlet.http.HttpServletRequest;

// import java.util.Arrays;

// @Aspect
// @Component
// public class RoleCheckAspect {

//     @Autowired
//     private JWTService jwtUtil;

//     @Around("@annotation(rolesAllowed)")
//     public Object checkRole(ProceedingJoinPoint joinPoint, RolesAllowed rolesAllowed) throws Throwable {
//         ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//         HttpServletRequest request = attributes.getRequest();
//         // Retrieve the required role from the annotation
//         MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//         RolesAllowed roleRequired = signature.getMethod().getAnnotation(RolesAllowed.class);
//         String requiredRole = roleRequired.value();
//         requiredRole = requiredRole.replaceAll("\\[", "").replaceAll("\\]", "");
//         // Split the string by commas
//         String[] roleList = requiredRole.split(",");
//         // Extract JWT token from request header
//         String authorizationHeader = request.getHeader("Authorization");
//         if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing or invalid Authorization header");
//         }
//         String token = authorizationHeader.substring(7);
//         Claims tokenData = jwtUtil.extractAllClaims(token);
//         String tokenRole = (String) tokenData.get("role");
        

//         boolean roleExists = Arrays.stream(roleList)
//                 .anyMatch(role -> role.equals(tokenRole));

//         if (!roleExists) {
//             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role does not match required role");
//         }

//         System.out.println("Checking the required role: " + Arrays.toString(roleList));

//         // Proceed with the original method if the role is allowed
//         return joinPoint.proceed();
//     }
// }
