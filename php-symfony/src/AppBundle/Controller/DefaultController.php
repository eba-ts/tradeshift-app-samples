<?php

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Session\Session;

class DefaultController extends SecurityController
{

    /**
     * Start page
     *
     * @Route("/", name="homepage")
     */
    public function indexAction(Request $request)
    {
        $session = new Session();

        // if no token in session - get it
        if (!$session->get('token') )
        {
            return $this->obtainAuthCodeAction();
        }

        // start AngularJs App
        return $this->redirect('/frontend/index.html');
    }
}
