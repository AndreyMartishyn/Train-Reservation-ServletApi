package ua.martishyn.app.data.utils.constants;

public class ViewConstants {
    private ViewConstants(){}
    private static final String JSP_LOC = "/view/";

    public static final String ABOUT_PAGE = JSP_LOC + "about_us.jsp";
    public static final String HOME_PAGE = "index.jsp";
    public static final String LOGIN_PAGE = JSP_LOC + "login.jsp";
    public static final String REGISTER_PAGE = JSP_LOC + "register.jsp";

    public static final String ADMIN_STATIONS = JSP_LOC + "admin/admin_stations.jsp";
    public static final String ADMIN_ADD_EDIT_STATIONS = JSP_LOC + "admin/admin_add_edit_station.jsp";

    public static final String ADMIN_USERS = JSP_LOC + "admin/admin_users.jsp";
    public static final String ADMIN_USERS_EDIT = JSP_LOC + "admin/admin_edit_user.jsp";

    public static final String ADMIN_ROUTES = JSP_LOC + "admin/admin_routes.jsp";
    public static final String ADMIN_ROUTE_ADD_EDIT = JSP_LOC + "admin/admin_add_edit_single_route.jsp";

    public static final String ADMIN_WAGONS= JSP_LOC + "admin/admin_wagons.jsp";
    public static final String ADMIN_EDIT_WAGON= JSP_LOC + "admin/admin_edit_wagon.jsp";
    public static final String ADMIN_ADD_WAGON= JSP_LOC + "admin/admin_add_wagon.jsp";

    public static final String CUSTOMER_TICKETS_PAGE = JSP_LOC + "customer/customer_tickets_page.jsp";
    public static final String CUSTOMER_TICKETS_FORM = JSP_LOC + "customer/customer_ticket_buy.jsp";
    public static final String CUSTOMER_TICKETS_FORM_ERROR = JSP_LOC + "customer/customer_ticket_buy_error_page.jsp";

    public static final String CUSTOMER_ROUTE_VIEW = JSP_LOC + "customer/customer_route_view.jsp";

}
