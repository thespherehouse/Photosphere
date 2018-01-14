//
//  AuthenticationServices.swift
//  Photosphere
//
//  Created by Soham Ray on 29/12/17.
//  Copyright © 2017 TheSphereHouse. All rights reserved.
//

import Foundation
import Alamofire
import ObjectMapper

typealias noDataCompletion = (_ response: GenericResponse<EmptyResponse>) -> Void
typealias loginCompletion = (_ status: Bool, _ error : String?) -> Void
typealias registerCompletion = (_ status: Bool, _ error : String?) -> Void


class AuthenticationServices {
    static let shared = AuthenticationServices()
    init() {}
    
    func register(withName name: String, email: String, andPassword password: String, completion: @escaping registerCompletion) {
        let parameters = ["name": name,
                          "email": email,
                          "password": password]
        Alamofire.request(AuthenticationEndpoints.registration.url,
                          method: AuthenticationEndpoints.registration.method,
                          parameters: parameters,
                          encoding: JSONEncoding.default,
                          headers: CommonHeader.shared.header
            )
        .validate()
            .responseJSON { (json) in
                switch json.result {
                case .success(let value):
                    let result = Mapper<GenericResponse<EmptyResponse>>().map(JSON: value as! [String: Any])
                    guard let res = result else {
                        completion(false,"Data not found")
                        return
                    }
                    if res.error.code == 0 {
                        if let respHeader = json.response {
                            if let tok = respHeader.allHeaderFields["Token"] {
                                print(tok)
                                KeychainManager.shared.keychain.set(tok as! String, forKey: KeychainConstants.loginToken)
                                completion(true,nil)
                            } else {
                                // no token header
                                completion(false,"Data not found")
                            }
                        } else {
                            // no response header
                            completion(false,"Data not found")
                        }
                    } else {
                        completion(false,res.error.message)
                    }
                    break
                case .failure(let error):
                    completion(false,error.localizedDescription)
                    break
                }
        }
    }
    
    func login(withEmail email: String, andPassword password: String, completion: @escaping loginCompletion) {
        let parameters = ["email" : email,
                          "password" : password]
        
        Alamofire.request(AuthenticationEndpoints.login.url,
                          method: AuthenticationEndpoints.login.method,
                          parameters: parameters,
                          encoding: JSONEncoding.default,
                          headers: CommonHeader.shared.header
            )
        .validate()
        .responseJSON { (json) in
            switch json.result {
            case .success(let value):
                let result = Mapper<GenericResponse<EmptyResponse>>().map(JSON: value as! [String: Any])
                guard let res = result else {
                    completion(false,"Data not found")
                    return
                }
                if res.error.code == 0 {
                    if let respHeader = json.response {
                        if let tok = respHeader.allHeaderFields["Token"] {
                            print(tok)
                            KeychainManager.shared.keychain.set(tok as! String, forKey: KeychainConstants.loginToken)
                            completion(true,nil)
                        } else {
                            // no token header
                            completion(false,"Data not found")
                        }
                    } else {
                        // no response header
                        completion(false,"Data not found")
                    }
                } else {
                    completion(false,res.error.message)
                }

            case .failure(let error):
                completion(false,error.localizedDescription)
        }
    }
}
    
    func forgotPassword(withEmail: String) {
        
    }
    
    func checkOtp(withEmai email : String, andOtp otp : String) {
        
    }
}
