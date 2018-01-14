//
//  Endpoints.swift
//  Photosphere
//
//  Created by Soham Ray on 04/01/18.
//  Copyright © 2018 TheSphereHouse. All rights reserved.
//

import Foundation
import Alamofire

public protocol Endpoint {
    var path: String {get}
    var method: HTTPMethod {get}
    var url: String{get}
}

enum AuthenticationEndpoints: Endpoint {
    case login
    case registration
}

extension AuthenticationEndpoints {
    var path: String {
        switch self{
        case .login:
            return "/api/v1/auth/login"
        case .registration:
            return "/api/v1/auth/register"
        }
    }
    
    var method: HTTPMethod {
        switch self {
        case .login:
            return .put
        case .registration:
            return .post
        }
    }
    
    var url : String {
        return ServerConfig.baseURL + path
    }
}
