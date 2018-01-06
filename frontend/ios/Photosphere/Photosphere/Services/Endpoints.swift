//
//  Endpoints.swift
//  Photosphere
//
//  Created by Soham Ray on 04/01/18.
//  Copyright © 2018 TheSphereHouse. All rights reserved.
//

import Foundation
import Alamofire


enum AuthenticationServices {
    case login
    case registration
}

extension AuthenticationServices {
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
    
}
