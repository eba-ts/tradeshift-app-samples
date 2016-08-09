<?php
/**
 * Security controller
 *
 * Tradeshift Authorization and token management
 * Implements OAuth2.0 protocol
 */

namespace AppBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\HttpException;
use Symfony\Component\HttpFoundation\Session\Session;

use AppBundle\Resources\Models\TsRestAPI;
use AppBundle\Resources\Models\SecurityModel; //holds our authentication logic


class SecurityController extends Controller
{
    /**
     * Step 1 :
     * Redirect to Authorization Server to Obtain Auth Code
     *
     * @route ("/oauth2/code", name="oauth2_code")
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function obtainAuthCodeAction()
    {
        $securityModel = new SecurityModel();
        $responseUrl = $securityModel->sendGetCode(SecurityModel::getApiUrl());

        return $this->redirect($responseUrl);
    }

    /**
     * Step 2:
     * Get Auth Code here, then
     * Exchange Auth Code for Access Token
     * and save it to session
     *
     * @route ("/oauth2/callback", name="oauth2_code")
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function CodeForAccessTokenAction(Request $request)
    {
        $session = new Session();

        if ($request->get('code')) {
            $code = $request->get('code');

            $securityModel = new SecurityModel();
            $securityModel->sendGetToken( SecurityModel::TRADESHIFT_AUTH_TOKEN_URL, $code );

            // if token saved in session - back to home page
            if ($session->get('token')) {
                $this->setTSlocale();

                return $this->redirect('/');
            }
        }
        throw new HttpException(400, 'No "code" parameter in query present, or session not saved!');
    }

    /**
     * save TS language to locale
     * @return mixed
     */
    private function setTSlocale()
    {
        $tsRestApi = new TsRestAPI();
        $this->get('session')->set('_locale', $tsRestApi->getLanguage());
        return $this->get('session')->get('_locale');
    }
}